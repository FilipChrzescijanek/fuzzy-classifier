package pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;

public interface Reductor {

    FuzzyDataSet reduce(FuzzyDataSet dataSet);

}
