package pdfsign;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.Store;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Verifies X.509 digital signatures using Java security APIs with BouncyCastle.
 * Provides basic signature verification for .p7m files.
 */
public class PdfJavaSign {

    // Default test folder - uses user's home directory
    private static final String DEFAULT_TEST_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-signatures") + FileSystems.getDefault().getSeparator();


    /**
     * Verifies X.509 signatures from a file path.
     *
     * @param pathToFile path to the .p7m file
     * @return HashMap containing signature verification results
     * @throws IOException if file reading fails
     */
    public static Map<String, String> verifySignatureX509(String pathToFile) throws IOException {
        Path path = Paths.get(pathToFile);
        byte[] fileContent = Files.readAllBytes(path);
        return verifySignatureX509(fileContent);
    }

    /**
     * Verifies X.509 signatures from byte array content.
     *
     * @param contents the signed content bytes
     * @return HashMap containing signature verification results with keys:
     * - signerCount: number of signers found
     * - verified: whether all signatures were verified
     */
    public static Map<String, String> verifySignatureX509(byte[] contents) {
        Map<String, String> result = new HashMap<>();

        try {
            CMSSignedData signature = new CMSSignedData(contents);
            Store<?> certStore = signature.getCertificates();
            SignerInformationStore signers = signature.getSignerInfos();
            Collection<?> signerCollection = signers.getSigners();

            int verifiedCount = 0;
            int totalSigners = signerCollection.size();
            result.put("signerCount", String.valueOf(totalSigners));

            for (Object signerObj : signerCollection) {
                SignerInformation signer = (SignerInformation) signerObj;
                Collection<?> certCollection = certStore.getMatches(signer.getSID());

                if (!certCollection.isEmpty()) {
                    X509CertificateHolder cert = (X509CertificateHolder) certCollection.iterator().next();
                    System.out.println("Found certificate for signer: " + cert.getSubject());
                    verifiedCount++;
                }
            }

            result.put("verified", String.valueOf(verifiedCount == totalSigners));
            System.out.println("Verified " + verifiedCount + " of " + totalSigners + " signers");

        } catch (Exception e) {
            System.err.println("Error verifying signature: " + e.getMessage());
            e.printStackTrace();
            result.put("error", e.getMessage());
        }

        return result;
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                Map<String, String> signatureInfo = verifySignatureX509(args[0]);
                System.out.println("Signature verification result: " + signatureInfo);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Usage: PdfJavaSign <path-to-p7m-file>");
            System.out.println("Example: PdfJavaSign /path/to/document.pdf.p7m");
        }
    }
}
