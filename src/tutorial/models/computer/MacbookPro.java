package tutorial.models.computer;

import java.util.function.Function;

public class MacbookPro extends Computer {

    public MacbookPro() {
    }

    public MacbookPro(Integer age, String color, Integer year) {
        this.age = age;
        this.color = color;
        this.year = year;
    }

    @Override
    public Double calculateValue(Double initialValue) {
        Function<Double, Double> function = super::calculateValue;
        Double pcValue = function.apply(initialValue);
        return pcValue + (initialValue / 10);
    }
}
