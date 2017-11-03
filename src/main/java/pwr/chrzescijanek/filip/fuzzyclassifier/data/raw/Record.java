package pwr.chrzescijanek.filip.fuzzyclassifier.data.raw;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Record {

    private final String              clazz;
    private final Map<String, Double> attributes;

    public Record(String clazz, Map<String, Double> attributes) {
        this.clazz      = Objects.requireNonNull(clazz);
        this.attributes = Collections.unmodifiableMap(Objects.requireNonNull(attributes));
    }

    public String getClazz() {
        return clazz;
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

}
