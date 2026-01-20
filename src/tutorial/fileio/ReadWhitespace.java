package tutorial.fileio;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadWhitespace {

    public static void main(String[] args) {
        String path = "./sample.txt";
        char[] in = new char[1000];
        int size = 0;
        boolean isWhitespace = false;
        String team1 = "";
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            size = fr.read(in);

            System.out.print("Characters present: " + size + "\n");
            System.out.print("The file content is as follows:\n");

            int i = 0;
            // If there are spaces, skip them
            for (i = 1; i < size; i++) {

                isWhitespace = Character.isWhitespace(in[i]);
                team1 = team1 + in[i];
                if (!isWhitespace) {
                    System.out.print(in[i]);  // Print file content without spaces
                }
            }
            System.out.print("\nString = " + team1);
            fr.close();
            System.out.print("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
