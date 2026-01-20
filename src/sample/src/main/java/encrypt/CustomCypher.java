package encrypt;

import javax.crypto.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CustomCypher {

    public static void main(String[] args) {
        try {

            String text = "Hello cypher";

            // Create key random
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            int keyBitSize = 128;
            keyGenerator.init(keyBitSize, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();

            //Key aesKey = new SecretKeySpec(secretKey.getEncoded(), "AES");
            // Initialize cipher
            Cipher cipher = Cipher.getInstance("AES");

            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            System.err.println(new String(encrypted));

            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            String decrypted = new String(cipher.doFinal(encrypted));
            System.err.println(decrypted);
	         
	         /*
	         byte[] encoded = Base64.getUrlDecoder().decode(encrypted);
	         System.out.println(new String(encoded));
	         */
            System.out.println(URLEncoder.encode(new String(encrypted), StandardCharsets.UTF_8));


        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            e.printStackTrace();
        }
    }

}
