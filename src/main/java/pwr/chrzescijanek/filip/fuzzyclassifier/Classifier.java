package pwr.chrzescijanek.filip.fuzzyclassifier;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;

public interface Classifier {

    Classifier train(DataSet dataSet);
    void       test (TestDataSet dataSet);

}
