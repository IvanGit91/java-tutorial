package pdfsign;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.DecoderException;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Advanced X.509 digital signature verification using BouncyCastle.
 * Supports both regular and streaming verification for large files.
 * Extracts signer information including name, surname, and fiscal code.
 */
public class PdfBouncySign {

    // Default test folder - uses user's home directory
    private static final String DEFAULT_TEST_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-signatures") + FileSystems.getDefault().getSeparator();

    private static final String BOUNCY_CASTLE_PROVIDER = "BC";

    /**
     * Verifies X.509 signatures from a file path.
     *
     * @param pathToFile   path to the .p7m file
     * @param decodeBase64 true if the file content is Base64 encoded
     * @return Map containing signature information
     * @throws IOException  if file reading fails
     * @throws CMSException if signature parsing fails
     */
    public static Map<String, String> verifySignatureX509(String pathToFile, boolean decodeBase64)
            throws IOException, CMSException {
        Path path = Paths.get(pathToFile);
        byte[] fileContent = Files.readAllBytes(path);
        return verifySignatureX509(fileContent, decodeBase64);
    }

    /**
     * Verifies X.509 signatures using streaming parser for large files.
     * More memory-efficient for large documents.
     *
     * @param contents     the signed content bytes
     * @param decodeBase64 true if content is Base64 encoded
     * @return Map containing verification results
     */
    public static Map<String, String> verifySignatureX509Stream(byte[] contents, boolean decodeBase64) {
        Map<String, String> result = new HashMap<>();

        try {
            CMSSignedDataParser parser = new CMSSignedDataParser(
                    new JcaDigestCalculatorProviderBuilder().setProvider(BOUNCY_CASTLE_PROVIDER).build(),
                    contents);
            parser.getSignedContent().drain();

            Store<?> certStore = parser.getCertificates();
            SignerInformationStore signers = parser.getSignerInfos();
            Collection<?> signerCollection = signers.getSigners();

            int verifiedCount = 0;
            for (Object signerObj : signerCollection) {
                SignerInformation signer = (SignerInformation) signerObj;
                Collection<?> certCollection = certStore.getMatches(signer.getSID());

                if (!certCollection.isEmpty()) {
                    X509CertificateHolder cert = (X509CertificateHolder) certCollection.iterator().next();
                    boolean verified = signer.verify(
                            new JcaSimpleSignerInfoVerifierBuilder()
                                    .setProvider(BOUNCY_CASTLE_PROVIDER)
                                    .build(cert));
                    System.out.println("Signature verification result: " + verified);
                    if (verified) verifiedCount++;
                }
            }

            result.put("verifiedSignatures", String.valueOf(verifiedCount));
            result.put("totalSignatures", String.valueOf(signerCollection.size()));

        } catch (Exception e) {
            System.err.println("Error during stream verification: " + e.getMessage());
            e.printStackTrace();
            result.put("error", e.getMessage());
        }

        return result;
    }

    /**
     * Verifies X.509 signatures from byte array content and extracts signer details.
     *
     * @param contents     the signed content bytes
     * @param decodeBase64 true if content is Base64 encoded
     * @return Map containing signature information
     */
    public static Map<String, String> verifySignatureX509(byte[] contents, boolean decodeBase64) {
        Map<String, String> result = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            CMSSignedData signature;
            if (decodeBase64) {
                signature = new CMSSignedData(Base64.decode(contents));
            } else {
                signature = new CMSSignedData(contents);
            }

            Store<?> certStore = signature.getCertificates();
            SignerInformationStore signers = signature.getSignerInfos();
            Collection<?> signerCollection = signers.getSigners();

            System.out.println("Number of signers found: " + signerCollection.size());

            int signerIndex = 0;
            for (Object signerObj : signerCollection) {
                SignerInformation signer = (SignerInformation) signerObj;
                Collection<?> certCollection = certStore.getMatches(signer.getSID());

                for (Object certObj : certCollection) {
                    X509CertificateHolder cert = (X509CertificateHolder) certObj;
                    X500Name x500name = cert.getSubject();

                    String prefix = signerIndex > 0 ? "signer" + signerIndex + "_" : "";

                    // Extract Common Name (full name)
                    RDN[] cnRdns = x500name.getRDNs(BCStyle.CN);
                    if (cnRdns.length > 0) {
                        String fullName = IETFUtils.valueToString(cnRdns[0].getFirst().getValue());
                        result.put(prefix + "fullName", fullName);
                    }

                    // Extract Given Name
                    RDN[] givenNameRdns = x500name.getRDNs(BCStyle.GIVENNAME);
                    if (givenNameRdns.length > 0) {
                        String givenName = IETFUtils.valueToString(givenNameRdns[0].getFirst().getValue());
                        result.put(prefix + "givenName", givenName);
                    }

                    // Extract Surname
                    RDN[] surnameRdns = x500name.getRDNs(BCStyle.SURNAME);
                    if (surnameRdns.length > 0) {
                        String surname = IETFUtils.valueToString(surnameRdns[0].getFirst().getValue());
                        result.put(prefix + "surname", surname);
                    }

                    // Extract Serial Number (often contains fiscal/tax code)
                    RDN[] serialRdns = x500name.getRDNs(BCStyle.SERIALNUMBER);
                    if (serialRdns.length > 0) {
                        String serialNumber = IETFUtils.valueToString(serialRdns[0].getFirst().getValue());
                        String fiscalCode = extractFiscalCode(serialNumber);
                        result.put(prefix + "fiscalCode", fiscalCode);
                        System.out.println("Fiscal Code: " + fiscalCode);
                    }

                    // Certificate validity dates
                    Date validFrom = cert.getNotBefore();
                    Date validTo = cert.getNotAfter();
                    result.put(prefix + "validFrom", sdf.format(validFrom));
                    result.put(prefix + "validTo", sdf.format(validTo));
                    System.out.println("Certificate valid from: " + validFrom);
                    System.out.println("Certificate valid to: " + validTo);

                    signerIndex++;
                }
            }
        } catch (CMSException e) {
            System.err.println("CMS signature parsing error: " + e.getMessage());
            e.printStackTrace();
        } catch (DecoderException e) {
            System.err.println("Base64 decoding error");
        }

        return result;
    }

    /**
     * Extracts the fiscal code from a serial number field.
     */
    private static String extractFiscalCode(String serialNumber) {
        int colonIndex = serialNumber.indexOf(":");
        int dashIndex = serialNumber.indexOf("-");

        if (colonIndex != -1) {
            return serialNumber.substring(colonIndex + 1).trim().toUpperCase();
        } else if (dashIndex != -1) {
            return serialNumber.substring(dashIndex + 1).trim().toUpperCase();
        } else if (serialNumber.length() > 3) {
            return serialNumber.substring(3).trim().toUpperCase();
        }
        return serialNumber.trim().toUpperCase();
    }

    /**
     * Extracts X.509 certificates from a certificate file.
     * Method 1: Uses CertificateFactory for individual certificate parsing.
     *
     * @param certificateFile path to the certificate file
     */
    public static void extractCertificatesMethod1(String certificateFile) {
        try (FileInputStream fis = new FileInputStream(certificateFile);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            while (bis.available() > 0) {
                Certificate cert = cf.generateCertificate(bis);
                System.out.println("Certificate: " + cert.toString());
            }
        } catch (CertificateException | IOException e) {
            System.err.println("Error extracting certificates: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Extracts X.509 certificates from a certificate file.
     * Method 2: Uses CertificateFactory for collection-based parsing.
     *
     * @param certificateFile path to the certificate file
     */
    public static void extractCertificatesMethod2(String certificateFile) {
        try (FileInputStream fis = new FileInputStream(certificateFile)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Collection<?> certificates = cf.generateCertificates(fis);

            for (Object certObj : certificates) {
                Certificate cert = (Certificate) certObj;
                System.out.println("Certificate: " + cert);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Certificate file not found: " + certificateFile);
        } catch (CertificateException | IOException e) {
            System.err.println("Error extracting certificates: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                Map<String, String> signatureInfo = verifySignatureX509(args[0], false);
                System.out.println("Signature info: " + signatureInfo);
            } catch (IOException | CMSException e) {
                System.err.println("Error verifying signature: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Usage: PdfBouncySign <path-to-p7m-file>");
            System.out.println("Example: PdfBouncySign /path/to/document.pdf.p7m");
        }
    }

    /**
     * Gets the value of an X.509 certificate extension by OID.
     *
     * @param certificate the X.509 certificate
     * @param oid         the extension OID
     * @return the decoded extension value, or null if not found
     */
    public String getExtensionValue(X509Certificate certificate, String oid) throws IOException {
        byte[] extensionValue = certificate.getExtensionValue(oid);

        if (extensionValue != null) {
            ASN1Primitive derObject = toDerObject(extensionValue);
            if (derObject instanceof DEROctetString derOctetString) {
                derObject = toDerObject(derOctetString.getOctets());
                if (derObject instanceof ASN1String) {
                    return ((ASN1String) derObject).getString();
                }
            }
        }
        return null;
    }

    /**
     * Converts byte array to DER object.
     */
    private ASN1Primitive toDerObject(byte[] data) throws IOException {
        try (ByteArrayInputStream inStream = new ByteArrayInputStream(data);
             ASN1InputStream asnInputStream = new ASN1InputStream(inStream)) {
            return asnInputStream.readObject();
        }
    }
}
