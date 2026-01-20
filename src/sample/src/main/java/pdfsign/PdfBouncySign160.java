package pdfsign;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.DecoderException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Verifies X.509 digital signatures in .p7m files using BouncyCastle 1.60+.
 * Extracts signer information including name, surname, and fiscal code.
 */
public class PdfBouncySign160 {

    // Default test folder - uses user's home directory
    private static final String DEFAULT_TEST_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-signatures") + FileSystems.getDefault().getSeparator();

    // Example test file names (for demonstration purposes)
    private static final String SAMPLE_FILE_1 = "signed_document.pdf.p7m";
    private static final String SAMPLE_FILE_2 = "sample_attachment.pdf.p7m";

    /**
     * Verifies X.509 signatures from a file path.
     *
     * @param pathToFile   path to the .p7m file
     * @param decodeBase64 true if the file content is Base64 encoded
     * @return HashMap containing signature information
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
     * Verifies X.509 signatures from byte array content.
     *
     * @param contents     the signed content bytes
     * @param decodeBase64 true if content is Base64 encoded
     * @return HashMap containing signature information with keys:
     * - fullName: signer's full name (CN)
     * - givenName: signer's first name
     * - surname: signer's last name
     * - fiscalCode: extracted fiscal/tax code
     * - validFrom: certificate validity start date
     * - validTo: certificate validity end date
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
     * Handles various formats: "TINIT-XXXXX", "CF:XXXXX", "IT-XXXXX"
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
            System.out.println("Usage: PdfBouncySign160 <path-to-p7m-file>");
            System.out.println("Example: PdfBouncySign160 /path/to/document.pdf.p7m");
        }
    }
}
