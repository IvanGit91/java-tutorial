package tutorial.fileio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class WriteTxt {


    public static void main(String[] args) {
        String path = "./sample.txt";
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            fw.write("This is first file");
            fw.append("hello file");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
