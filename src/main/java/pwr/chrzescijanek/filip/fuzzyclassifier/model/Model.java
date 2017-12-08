package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import java.util.List;
import java.util.Map;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

public interface Model {

    Map<String, Double> getProbabilitiesFor(TestRecord testRecord);
    List<String>        getClazzValues     ();

}
