package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

public class NullModel<T> implements Model<T> {


    @Override
    public Map<String, T> getProbabilitiesFor(TestRecord testRecord) {
        return Collections.emptyMap();
    }

    @Override
    public List<String> getClazzValues() {
        return Collections.emptyList();
    }

}
