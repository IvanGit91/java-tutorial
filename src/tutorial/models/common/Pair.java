package tutorial.models.common;

/**
 * A generic pair class that holds two values of potentially different types.
 * This is a consolidated class replacing duplicate Coppia implementations.
 *
 * @param <T> the type of the first value
 * @param <K> the type of the second value
 */
public class Pair<T, K> {
    private T first;
    private K second;

    public Pair() {
    }

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Safely casts an object to the specified class type.
     *
     * @param object the object to cast
     * @param clazz  the target class type
     * @param <T>    the type parameter
     * @return the cast object or null if cast fails
     */
    public static <T> T safeCast(Object object, Class<T> clazz) {
        try {
            return clazz.cast(object);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public K getSecond() {
        return second;
    }

    public void setSecond(K second) {
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
        Pair<?, ?> other = (Pair<?, ?>) obj;
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
        return "Pair [first=" + first + ", second=" + second + "]";
    }
}
