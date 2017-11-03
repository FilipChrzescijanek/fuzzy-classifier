package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

public interface Rule {

    Double getProbabilityFor(TestRecord testRecord, Stats stats);
    String getClazz         ();

}
