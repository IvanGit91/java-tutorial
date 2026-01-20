package tutorial.string;

import java.text.MessageFormat;
import java.util.Locale;

public class Msg {
    public static void main(String[] args) {


        String msg = "test {0} {1} ok";


        System.out.println("F: " + new MessageFormat(msg, Locale.getDefault()).format(new String[]{"first", "second"}));

    }
}
