package tutorial.stream;

public class StringCombiner {

    private final String prefix;
    private final String suffix;
    private final String delim;
    private final StringBuilder builder;

    public StringCombiner(String delim, String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.delim = delim;
        this.builder = new StringBuilder();
    }

    public StringCombiner add(String word) {
        if (!this.areAtStart()) {
            this.builder.append(delim);
        }
        this.builder.append(word);

        return this;
    }

    public StringCombiner merge(StringCombiner other) {
        if (!other.equals(this)) {
            if (!other.areAtStart() && !this.areAtStart()) {
                other.builder.insert(0, this.delim);
            }
            this.builder.append(other.builder);
        }
        return this;
    }

    @Override
    public String toString() {
        return prefix + builder + suffix;
    }

    private boolean areAtStart() {
        return builder.length() == 0;
    }
}