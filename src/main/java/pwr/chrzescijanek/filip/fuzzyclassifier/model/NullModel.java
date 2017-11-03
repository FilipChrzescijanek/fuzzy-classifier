package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NullModel implements Model {

    @Override
    public Map<String, Double> getProbabilitiesFor(TestRecord testRecord) {
        return Collections.emptyMap();
    }

    @Override
    public List<String> getClazzValues() {
        return Collections.emptyList();
    }

}
