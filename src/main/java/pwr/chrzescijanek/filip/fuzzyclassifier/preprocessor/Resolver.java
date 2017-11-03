package pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;

public interface Resolver {

    FuzzyDataSet resolve(FuzzyDataSet dataSet);

}
