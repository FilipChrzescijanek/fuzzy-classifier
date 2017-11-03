package pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy;

import pwr.chrzescijanek.filip.fuzzyclassifier.model.common.FuzzySet;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

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
