package tutorial.fileio;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WhiteSpace {

    public static void main(String[] args) {
        String path = "./sample.txt";
        char[] in = new char[1000];
        int size = 0;
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            size = fr.read(in);

            System.out.print("Characters present: " + size + "\n");

            System.out.print("The file content is as follows:\n");
            boolean isWhitespace = false;
            String result = "";
            int i = 0;
            // If there are spaces, skip them
            for (i = 1; i < size; i++) {
                result = result + in[i]; // Save character by character to string
                isWhitespace = Character.isWhitespace(in[i]);
                if (!isWhitespace) {
                    System.out.print(in[i]);  // Print file content without spaces
                }
            }
            System.out.print("\nString = " + result);
            fr.close();
            System.out.print("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
