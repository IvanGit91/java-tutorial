package tutorial.exceptions;

public class DemoException {
    public static void main(String[] args) {
        DemoException DE = new DemoException();
        try {
            System.out.println(DE.sum(5, 1));
            System.out.println(DE.sum(6, 5));
            System.out.println(DE.sum(2, 2));
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Prints the first exception that occurs
        }
    }

    public int sum(int a, int b) throws Exception {
        int result = a + b;
        if (result > 10) throw new Exception("Sum:" + result + ">10 !!");
        if (result > 5) throw new Exception("Sum:" + result + ">5");
        return result;
    }
}