package tutorial.models.computer;

public class Computer {
    protected Integer age;
    protected String color;
    protected Integer year;

    public Computer() {
    }

    public Computer(Integer age, String color) {
        this.age = age;
        this.color = color;
    }

    public Computer(Integer age, String color, Integer year) {
        this.age = age;
        this.color = color;
        this.year = year;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void turnOnPc() {
        System.out.println("TURN: " + this.getAge());
    }

    public Double calculateValue(Double initialValue) {
        return initialValue / 1.50;
    }
}
