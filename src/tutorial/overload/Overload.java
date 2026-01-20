package tutorial.overload;

public class Overload {
    public static void main(String[] args) {
        Overload o = new Overload();
        System.out.println(o.add(1, 2));
        System.out.println(o.add(1.2f, 1.4f));
    }

    // Methods with the same name but different parameters
    public int add(int x, int y) {
        return x + y;
    }

    public float add(float x, float y) {
        return x + y;
    }

}
