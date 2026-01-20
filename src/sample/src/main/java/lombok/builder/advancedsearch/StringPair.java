package lombok.builder.advancedsearch;

/**
 * A specialized pair class for holding two String values.
 * This is a consolidated class replacing duplicate CoppiaStringhe implementations.
 */
public class StringPair {
    private String first;
    private String second;

    public StringPair() {
    }

    /**
     * Creates a StringPair where both values are set to the same string.
     *
     * @param value the value for both first and second
     */
    public StringPair(String value) {
        this.first = this.second = value;
    }

    public StringPair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
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
        StringPair other = (StringPair) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (second == null) {
            return other.second == null;
        } else return second.equals(other.second);
    }

    @Override
    public String toString() {
        return "StringPair [first=" + first + ", second=" + second + "]";
    }
}
