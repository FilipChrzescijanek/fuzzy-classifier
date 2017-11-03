package pwr.chrzescijanek.filip.fuzzyclassifier.data.test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TestDataSet {

    private final List<String>     attributes;
    private final List<TestRecord> testRecords;

    public TestDataSet(List<String> attributes, List<TestRecord> testRecords) {
        this.attributes  = Collections.unmodifiableList(Objects.requireNonNull(attributes));
        this.testRecords = Collections.unmodifiableList(Objects.requireNonNull(testRecords));
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<TestRecord> getTestRecords() {
        return testRecords;
    }

}
