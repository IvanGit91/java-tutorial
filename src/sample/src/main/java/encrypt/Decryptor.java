package encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Decryptor {

    public static void main(String[] args) {


        //String key = "Bar12345Bar12345"; // 128 bit key
        String key = myKey(30);
        System.out.println("MYKEY: " + key);

        // Create key and cipher
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES");

            // decrypt the text
            String decryptedStr = myDecrypt(cipher, aesKey, "RL/Lsy+eGgwVfk7gZMbyUw==");

            System.out.println("D: " + decryptedStr);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }


    public static String myKey(int seed) {
        char[] a = new char[16];
        int index;
        for (int i = 0; i < seed; i++) {
            index = i % 16;
            a[index] = (char) (a[index] + (i + seed));
        }
        return String.copyValueOf(a);
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
