package pdfsign;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

/**
 * PDF digital signer using Apache PDFBox and BouncyCastle.
 * Creates digitally signed PDF documents with optional timestamping.
 *
 * <p>Configuration can be provided via system properties or environment variables:
 * <ul>
 *   <li>pdf.signer.keystore - path to PKCS#12 keystore (default: classpath resource)</li>
 *   <li>pdf.signer.password - keystore password</li>
 *   <li>pdf.signer.alias - certificate alias in keystore</li>
 *   <li>pdf.signer.tsa.url - Time Stamping Authority URL</li>
 *   <li>pdf.signer.name - signer name for signature</li>
 *   <li>pdf.signer.reason - signing reason</li>
 *   <li>pdf.signer.location - signing location</li>
 * </ul>
 */
public class PdfSigner implements SignatureInterface {

    private static final String PROVIDER_BC = "BC";
    private static final String KEYSTORE_TYPE_PKCS12 = "PKCS12";
    private static final String HASH_ALGORITHM_SHA256 = "SHA-256";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final int DEFAULT_SIGNATURE_SIZE = 16000;

    // Timestamp token OID (RFC 3161)
    private static final String TIMESTAMP_TOKEN_OID = "1.2.840.113549.1.9.16.2.14";

    // Default configuration (can be overridden via system properties)
    private static final String DEFAULT_KEYSTORE_LOCATION = "/sample-keystore.p12";
    private static final String DEFAULT_TSA_URL = "https://freetsa.org/tsr";

    private final PrivateKey privateKey;
    private final Certificate[] certificateChain;
    private final String tsaUrl;
    private final String signerName;
    private final String signingReason;
    private final String signingLocation;

    /**
     * Creates a PdfSigner with default configuration from system properties.
     */
    public PdfSigner() {
        this(
                getConfig("pdf.signer.keystore", DEFAULT_KEYSTORE_LOCATION),
                getConfig("pdf.signer.password", "changeit"),
                getConfig("pdf.signer.alias", null),
                getConfig("pdf.signer.tsa.url", DEFAULT_TSA_URL),
                getConfig("pdf.signer.name", "Digital Signer"),
                getConfig("pdf.signer.reason", "Document signed digitally"),
                getConfig("pdf.signer.location", "Digital Signature")
        );
    }

    /**
     * Creates a PdfSigner with specified configuration.
     *
     * @param keystorePath    path to PKCS#12 keystore (classpath or file path)
     * @param password        keystore and key password
     * @param alias           certificate alias (null to use first entry)
     * @param tsaUrl          Time Stamping Authority URL (null to disable timestamping)
     * @param signerName      name to display in signature
     * @param signingReason   reason for signing
     * @param signingLocation location of signing
     */
    public PdfSigner(String keystorePath, String password, String alias,
                     String tsaUrl, String signerName, String signingReason, String signingLocation) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        this.tsaUrl = tsaUrl;
        this.signerName = signerName;
        this.signingReason = signingReason;
        this.signingLocation = signingLocation;

        try {
            KeyStore keystore = KeyStore.getInstance(KEYSTORE_TYPE_PKCS12, PROVIDER_BC);

            // Try classpath first, then file system
            InputStream input = PdfSigner.class.getResourceAsStream(keystorePath);
            if (input == null) {
                input = new FileInputStream(keystorePath);
            }

            keystore.load(input, password.toCharArray());
            input.close();

            // Use provided alias or first alias in keystore
            String effectiveAlias = alias;
            if (effectiveAlias == null || effectiveAlias.isEmpty()) {
                effectiveAlias = keystore.aliases().nextElement();
            }

            privateKey = (PrivateKey) keystore.getKey(effectiveAlias, password.toCharArray());
            certificateChain = keystore.getCertificateChain(effectiveAlias);

            if (privateKey == null || certificateChain == null) {
                throw new RuntimeException("Could not load private key or certificate chain for alias: " + effectiveAlias);
            }

        } catch (KeyStoreException | NoSuchProviderException | IOException |
                 NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException e) {
            throw new RuntimeException("Error while loading certificates and private key", e);
        }
    }

    private static String getConfig(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key.replace(".", "_").toUpperCase());
        }
        return value != null ? value : defaultValue;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: PdfSigner <source-pdf> <destination-pdf>");
            System.out.println();
            System.out.println("Configuration via system properties:");
            System.out.println("  -Dpdf.signer.keystore=<path>     Path to PKCS#12 keystore");
            System.out.println("  -Dpdf.signer.password=<password> Keystore password");
            System.out.println("  -Dpdf.signer.alias=<alias>       Certificate alias");
            System.out.println("  -Dpdf.signer.tsa.url=<url>       TSA URL for timestamping");
            System.out.println("  -Dpdf.signer.name=<name>         Signer name");
            System.out.println("  -Dpdf.signer.reason=<reason>     Signing reason");
            System.out.println("  -Dpdf.signer.location=<location> Signing location");
            return;
        }

        PdfSigner signer = new PdfSigner();
        signer.signPdf(args[0], args[1]);
    }

    /**
     * Signs a PDF document.
     *
     * @param sourcePath      path to the source PDF
     * @param destinationPath path for the signed PDF output
     */
    public void signPdf(String sourcePath, String destinationPath) {
        File sourceFile = new File(sourcePath);
        File destFile = new File(destinationPath);

        // Create output directory if needed
        destFile.getParentFile().mkdirs();

        try {
            // Copy source to destination first
            try (FileInputStream fis = new FileInputStream(sourceFile);
                 FileOutputStream fos = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            // Load and sign the document
            try (PDDocument doc = Loader.loadPDF(destFile);
                 FileOutputStream fos = new FileOutputStream(destFile)) {

                PDSignature signature = new PDSignature();
                signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
                signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
                signature.setName(signerName);
                signature.setReason(signingReason);
                signature.setLocation(signingLocation);
                signature.setSignDate(Calendar.getInstance());

                SignatureOptions signatureOptions = new SignatureOptions();
                signatureOptions.setPreferredSignatureSize(DEFAULT_SIGNATURE_SIZE);
                doc.addSignature(signature, this, signatureOptions);

                doc.saveIncremental(fos);
            }

            System.out.println("PDF signed successfully: " + destinationPath);

        } catch (IOException e) {
            throw new RuntimeException("Error while signing PDF: " + sourcePath, e);
        }
    }

    @Override
    public byte[] sign(InputStream content) throws IOException {
        byte[] contentBytes = IOUtils.toByteArray(content);

        try {
            CMSSignedDataGenerator generator = new CMSSignedDataGenerator();

            ContentSigner signer = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM)
                    .setProvider(PROVIDER_BC)
                    .build(privateKey);

            generator.addSignerInfoGenerator(
                    new JcaSignerInfoGeneratorBuilder(
                            new JcaDigestCalculatorProviderBuilder()
                                    .setProvider(PROVIDER_BC)
                                    .build())
                            .build(signer, (X509Certificate) certificateChain[0]));

            Store certs = new JcaCertStore(Arrays.asList(certificateChain));
            generator.addCertificates(certs);

            CMSTypedData msg = new CMSProcessableByteArray(contentBytes);
            CMSSignedData signedData = generator.generate(msg, false);

            // Add timestamp if TSA URL is configured
            if (tsaUrl != null && !tsaUrl.isEmpty()) {
                signedData = addTimestamp(signedData);
            }

            // Transcode BER to DER
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ASN1OutputStream.create(baos, ASN1Encoding.DER).writeObject(signedData.toASN1Structure());
            return baos.toByteArray();

        } catch (Exception e) {
            System.err.println("Error while creating PKCS#7 signature: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Problem while preparing signature", e);
        }
    }

    private CMSSignedData addTimestamp(CMSSignedData signedData) throws Exception {
        Collection<?> signers = signedData.getSignerInfos().getSigners();
        SignerInformation signerInfo = (SignerInformation) signers.iterator().next();

        TimeStampClient timeStampClient = new TimeStampClient(tsaUrl, null, null);

        MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM_SHA256, PROVIDER_BC);
        byte[] digest = md.digest(signerInfo.getSignature());
        byte[] timestampToken = timeStampClient.stamp(digest);

        ASN1InputStream tempStream = new ASN1InputStream(new ByteArrayInputStream(timestampToken));
        ASN1Sequence seq = (ASN1Sequence) tempStream.readObject();
        tempStream.close();

        DERSet ds = new DERSet(seq);
        Attribute timestampAttribute = new Attribute(new ASN1ObjectIdentifier(TIMESTAMP_TOKEN_OID), ds);

        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(timestampAttribute);
        AttributeTable unsignedAttributes = new AttributeTable(vector);

        signerInfo = SignerInformation.replaceUnsignedAttributes(signerInfo, unsignedAttributes);

        @SuppressWarnings("unchecked")
        Collection<SignerInformation> newSigners = (Collection<SignerInformation>) signers;
        newSigners.clear();
        newSigners.add(signerInfo);

        return CMSSignedData.replaceSigners(signedData, new SignerInformationStore(newSigners));
    }
}
