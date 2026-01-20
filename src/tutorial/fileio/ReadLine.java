package tutorial.fileio;

import java.io.*;

public class ReadLine {


    public static void main(String[] args) {
        String path = "./sample.txt";
        try {
            File f = new File(path);
            FileInputStream is = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            //sb.delete(0, sb.length()); delete all the content of the stringbuilder
            String result = sb.toString();
            System.out.println(result);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
