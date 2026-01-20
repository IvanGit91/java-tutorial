package encrypt;

public class CustomKey {

    public static void main(String[] args) {

        char a = 'a';
        char a1 = 65;

        System.out.println("A: " + a);
        System.out.println("A1: " + (char) (a1 + 1));
        System.out.println("A2: " + (char) 65);

        Character.toString(a);

        char[] a2 = {2, 3, 'c'};
        System.out.println("A21: " + a2[0]);

        String key = myKey(30);

        System.out.println("MYKEY: " + key);

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

}
