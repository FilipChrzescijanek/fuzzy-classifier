package pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;

public interface Fuzzifier {

    FuzzyDataSet fuzzify(DataSet dataSet, Stats stats);

}
