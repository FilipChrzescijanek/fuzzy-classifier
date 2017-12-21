package pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;

public class FuzzyRecord {

    private final String                clazz;
    private final Map<String, FuzzySet> attributes;

    public FuzzyRecord(String clazz, Map<String, FuzzySet> attributes) {
        this.clazz      = Objects.requireNonNull(clazz);
        this.attributes = Collections.unmodifiableMap(Objects.requireNonNull(attributes));
    }

    public String getClazz() {
        return clazz;
    }

    public Map<String, FuzzySet> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuzzyRecord record = (FuzzyRecord) o;
        return Objects.equals(getClazz(), record.getClazz()) &&
                Objects.equals(getAttributes(), record.getAttributes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClazz(), getAttributes());
    }
}
