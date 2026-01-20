package random;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class MainRand {

    public static void main(String[] args) {
        // Create an insecure generator for 8-character identifiers:
        RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        System.out.println(gen.nextString());

        // Create a secure generator for session identifiers:
        RandomString session = new RandomString();
        System.out.println(session.nextString());

        // Create a generator with easy-to-read codes for printing. The strings are longer than full alphanumeric strings to compensate for using fewer symbols:
        String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
        RandomString tickets = new RandomString(23, new SecureRandom(), easy);
        System.out.println(tickets.nextString());
    }

}
