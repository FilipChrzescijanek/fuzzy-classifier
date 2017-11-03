package pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FuzzyDataSet {

    private final String            clazzAttribute;
    private final List<String>      clazzValues;
    private final List<String>      attributes;
    private final List<FuzzyRecord> records;

    public FuzzyDataSet(String clazzAttribute, List<String> clazzValues,
                        List<String> attributes, List<FuzzyRecord> records) {
        this.clazzAttribute = Objects.requireNonNull(clazzAttribute);
        this.clazzValues    = Collections.unmodifiableList(Objects.requireNonNull(clazzValues));
        this.attributes     = Collections.unmodifiableList(Objects.requireNonNull(attributes));
        this.records        = Collections.unmodifiableList(Objects.requireNonNull(records));
    }

    public String getClazzAttribute() {
        return clazzAttribute;
    }

    public List<String> getClazzValues() {
        return clazzValues;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<FuzzyRecord> getRecords() {
        return records;
    }

}
