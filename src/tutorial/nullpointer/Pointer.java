package tutorial.nullpointer;

import java.util.Arrays;

public class Pointer {

    public static void main(String[] args) {
        Sample p = new Sample(2);
        Sample p2 = new Sample();
        p2.setNumber(null);
        System.out.println("log: " + notNullValue(p.getNumber()).getClass());
        String[] strings = new String[]{notNullValue(p.getNumber()), notNullValue(p2.getNumber()) + notNullValue(2)};
        System.out.println(Arrays.toString(strings));

    }

    public static <T> String notNullValue(T value) {
        try {
            System.out.println("Value " + value.getClass());
            if (value instanceof Integer) {
                System.out.println("ok");
            }
            return value == null || value.equals("") ? "" : value.toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

    static class Sample {
        private String name;
        private Integer number;

        public Sample() {
        }

        public Sample(String name) {
            this.name = name;
        }

        public Sample(Integer number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}
