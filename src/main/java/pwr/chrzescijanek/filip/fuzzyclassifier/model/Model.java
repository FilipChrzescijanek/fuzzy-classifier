package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

import java.util.List;
import java.util.Map;

public interface Model {

    Map<String, Double> getProbabilitiesFor(TestRecord testRecord);
    List<String>        getClazzValues     ();

}
