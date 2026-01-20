package tutorial.models.vehicle;

/**
 * Represents an engine with brand, year, and horsepower specifications.
 * Used for demonstrating list operations.
 */
public class Engine {
    private String brand;
    private Integer year;
    private String horsepower;

    public Engine() {
    }

    public Engine(String brand, Integer year, String horsepower) {
        super();
        this.brand = brand;
        this.year = year;
        this.horsepower = horsepower;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        result = prime * result + ((horsepower == null) ? 0 : horsepower.hashCode());
        result = prime * result + ((brand == null) ? 0 : brand.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Engine other = (Engine) obj;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        if (horsepower == null) {
            if (other.horsepower != null)
                return false;
        } else if (!horsepower.equals(other.horsepower))
            return false;
        if (brand == null) {
            return other.brand == null;
        } else return brand.equals(other.brand);
    }

    @Override
    public String toString() {
        return "Engine [brand=" + brand + ", year=" + year + ", horsepower=" + horsepower + "]";
    }
}
