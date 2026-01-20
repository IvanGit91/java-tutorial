package encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CustomCypherBase64 {

    private static Cipher cipher;

    public static void main(String[] args) {

        String text = "Hello cypher";
        try {
            // Create key random
            KeyGenerator keyGenerator;
            keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            int keyBitSize = 128;
            keyGenerator.init(keyBitSize, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();

            //Key aesKey = new SecretKeySpec(secretKey.getEncoded(), "AES");
            // Initialize cipher
            cipher = Cipher.getInstance("AES");

            // encrypt the text and covert in base64 string readable
            String cryptedStr = myEncrypt(cipher, secretKey, text);

            System.out.println("E: " + cryptedStr);

            // decrypt the text
            String decryptedStr = myDecrypt(cipher, secretKey, cryptedStr);

            System.out.println("D: " + decryptedStr);

            //System.out.println(URLEncoder.encode(new String(encrypted), "UTF-8"));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    // encrypt the text and covert in base64 string readable
    public static String myEncrypt(Cipher cipher, Key secretKey, String valore) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(valore.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String myDecrypt(Cipher cipher, Key secretKey, String valore) {
        byte[] decodeString = Base64.decodeBase64(valore);
        // decrypt the text
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(decodeString));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
