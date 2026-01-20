package tutorial.models.vehicle;

/**
 * Represents a wheel with type and model specifications.
 * Used for demonstrating list operations.
 */
public class Wheel {
    private String type;
    private String model;

    public Wheel() {
    }

    public Wheel(String type, String model) {
        super();
        this.type = type;
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Wheel other = (Wheel) obj;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (type == null) {
            return other.type == null;
        } else return type.equals(other.type);
    }

    @Override
    public String toString() {
        return "Wheel [type=" + type + ", model=" + model + "]";
    }
}
