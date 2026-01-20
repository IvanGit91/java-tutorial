package tutorial.models.vehicle;

/**
 * Represents a vehicle with model, color, wheel, and engine components.
 */
public class Vehicle {

    private String model;
    private String color;
    private Wheel wheel;
    private Engine engine;

    public Vehicle() {
    }

    public Vehicle(String model, String color, Wheel wheel, Engine engine) {
        super();
        this.model = model;
        this.color = color;
        this.wheel = wheel;
        this.engine = engine;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((engine == null) ? 0 : engine.hashCode());
        result = prime * result + ((wheel == null) ? 0 : wheel.hashCode());
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
        Vehicle other = (Vehicle) obj;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (engine == null) {
            if (other.engine != null)
                return false;
        } else if (!engine.equals(other.engine))
            return false;
        if (wheel == null) {
            return other.wheel == null;
        } else return wheel.equals(other.wheel);
    }

    @Override
    public String toString() {
        return "Vehicle [model=" + model + ", color=" + color + ", wheel=" + wheel + ", engine=" + engine + "]";
    }
}
