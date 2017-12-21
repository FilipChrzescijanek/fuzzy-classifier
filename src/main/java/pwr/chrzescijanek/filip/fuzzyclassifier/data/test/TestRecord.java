package pwr.chrzescijanek.filip.fuzzyclassifier.data.test;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class TestRecord {

    private final Map<String, Double> attributes;

    private Double value;

    public TestRecord(Map<String, Double> attributes) {
        this.attributes = Collections.unmodifiableMap(Objects.requireNonNull(attributes));
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

    public Double getValue() {
        return Optional.ofNullable(value).orElse(Double.NaN);
    }

    public void setValue(Double value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
    	return attributes.toString();
    }

}
