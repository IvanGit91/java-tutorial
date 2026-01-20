package tutorial.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

    public static void main(String[] args) {

        String value = "john@ivan.pec.it";
        String value2 = "john@pec.ivan.it";
        String value3 = "john@pec.lop.ivan.it";
        String value4 = "demo@pec.demo.it";

        String patternString = "[\\w\\.-]*@[\\w-]*\\.(pec|cert|legalmail|arubapec|postecert|mypec|gigapec|casellapec|pecditta|pec-legal|pec-medici|pec-avvocati|)\\.(com|net|‌​org|edu|it|eu)";

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(value3);

        boolean matches = matcher.matches();


        String patternString2 = "[\\w\\.-]*@(pec|cert|legalmail|arubapec|postecert|mypec|gigapec|casellapec|pecditta|pec-legal|pec-medici|pec-avvocati|)\\.[\\w-]*\\.(com|net|‌​org|edu|it|eu)";

        Pattern pattern2 = Pattern.compile(patternString2);

        Matcher matcher2 = pattern2.matcher(value3);

        boolean matches2 = matcher2.matches();

        System.out.println("Result: " + (matches || matches2));
        System.out.println("Result: " + (!matches && !matches2));


        String patternString3 = "^[a-zA-Z0-9]{16}$";

        Pattern pattern3 = Pattern.compile(patternString3);

        Matcher matcher3 = pattern3.matcher("BBTTNKNSDNKMDOSA");

        boolean matches3 = matcher3.matches();

        System.out.println("Result3: " + matches3);


        String patternString4 = "(ab|po)|(pa|lo)";

        Pattern pattern4 = Pattern.compile(patternString4);

        Matcher matcher4 = pattern4.matcher("ab");  //true - abpa = false

        boolean matches4 = matcher4.matches();

        System.out.println("Result4: " + matches4);

        String patternString5 = "(^[a-zA-Z0-9]{11}$|^[a-zA-Z0-9]{16}$)";

        Pattern pattern5 = Pattern.compile(patternString5);

        Matcher matcher5 = pattern5.matcher("12345678916236698");  //true - abpa = false

        boolean matches5 = matcher5.matches();

        System.out.println("Result5: " + matches5);

    }

}
