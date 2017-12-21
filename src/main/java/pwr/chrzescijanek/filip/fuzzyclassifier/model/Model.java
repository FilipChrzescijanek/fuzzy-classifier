package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import java.util.List;
import java.util.Map;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

public interface Model<T> {

    Map<String, T> getProbabilitiesFor(TestRecord testRecord);
    List<String>   getClazzValues     ();

}
