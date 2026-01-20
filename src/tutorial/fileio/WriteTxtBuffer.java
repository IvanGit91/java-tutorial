package tutorial.fileio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class WriteTxtBuffer {


    public static void main(String[] args) {
        String path = "./sample.txt";
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            String content = "ASICSSA ORDINATA";
            double value = 1.0;
            value = value + 0.1;
            System.out.println(value);
            buffer(bw);
            bw.write(content);
            bw.newLine();
            bw.write(content);

            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void buffer(BufferedWriter d) throws IOException {
        d.write("demo line");
    }
}
