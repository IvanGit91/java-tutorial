package tutorial.fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadTxtBuffer {

    public static void main(String[] args) {
        String path = "./sample.txt";
        char[] in = new char[50];
        int size = 0;
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            size = br.read(in);

            System.out.print("Characters present: " + size + "\n");

            System.out.print("The file content is as follows:\n");

            for (int i = 0; i < size; i++)
                System.out.print(in[i]);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
