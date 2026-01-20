package tutorial.string;

public class StringTest {

    public static void main(String[] args) {

        String test = "GIEMMECI S.R.L. ";

        String parseCompanyName = test.replaceAll("[$&+,:;=?@#|'<>.^*()%!-\\/]", "_");

        System.out.println("PARSED: " + parseCompanyName);

        String test2 = "GIEMMECI S.R.L. ";

        String parseCompanyName2 = test2.replaceAll("[$&+,:;=?@#|'<>.^*()%!-\\/]", "_").trim();

        System.out.println("PARSEDtrim: " + parseCompanyName2);

        String test3 = "ivan@pec.it";

        test3 = test3.replaceAll("[$&+,:;=?@#|'<>.^*()%!-\\/]", "_").trim();

        System.out.println("TEST3: " + test3);

        System.out.println("TEST4 " + test.replaceAll("\\s+", "_")); // Replaces whitespace with _

        System.out.println("TEST5 " + test.replaceAll("[$&+,:;=?@#|'<>.^*()%!-\\/\\s+]", "_"));

    }

}
