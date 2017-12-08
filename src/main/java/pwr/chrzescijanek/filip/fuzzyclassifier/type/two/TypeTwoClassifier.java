package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import pwr.chrzescijanek.filip.fuzzyclassifier.AbstractClassifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSetStats;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Model;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Reductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Resolver;

public class TypeTwoClassifier extends AbstractClassifier {

    private TypeTwoClassifier(TypeTwoFuzzifier fuzzifier, Resolver resolver, Reductor reductor,
                              Defuzzifier defuzzifier) {
        super(defuzzifier, resolver, fuzzifier, reductor);
    }

    @Override
    protected Model createModel(DataSetStats stats, FuzzyDataSet fuzzyDataSet) {
        return new TypeTwoModel(fuzzyDataSet, stats);
    }

    public static class Builder extends AbstractClassifier.Builder {

        public Builder(TypeTwoFuzzifier fuzzifier, Resolver resolver, Reductor reductor) {
            super(fuzzifier, resolver, reductor);
        }

        @Override
        public AbstractClassifier build() {
            return new TypeTwoClassifier((TypeTwoFuzzifier) fuzzifier, resolver, reductor, defuzzifier);
        }

    }

}
