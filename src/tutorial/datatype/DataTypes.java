package tutorial.datatype;

public class DataTypes {

    public static void main(String[] args) {
        System.out.println("FILE DATATYPES\n");
        short s = 32767;
        short s1 = (short) (1 + s);  // Short = -32768<=x<=32767
        System.out.println("Short s " + s + " Short s1 " + s1);

        //float z = 3.14159; ERROR
        // CORRECT FORM IS THE FOLLOWING
        float z = 3.14159f;
        // OR
        float t = (float) (3.14159);
        // D1-D2-D3 LESSON 9
        byte b = 5;
        char c = '5';
        short ss = 13;
        int i = 555;
        float ff = 555.5f;
        b = (byte) ss;
        i = c;
        System.out.println("Integer: " + i);
        ff = i;
        //   if (ff>b)         IS EXECUTED
        //    System.exit(i);
        byte b1 = 3;
        b = (byte) (b * b1);
        System.out.println("byte: " + b);
        long result = b * ++ss; //int,long,float,double

        int[] x = new int[25];
        System.out.println("Value of x = " + x + " Value of x[0] = " + x[0] + " Length = " + x.length);

        int h, j = 6, k = 7;
        h = j++ + k++;
        System.out.println(h + " " + j + " " + k);

        int x2 = 6;
        if (!(x2 > 3)) {

        }

    }

}
