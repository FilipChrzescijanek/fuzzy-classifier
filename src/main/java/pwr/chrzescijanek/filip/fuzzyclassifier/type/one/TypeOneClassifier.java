package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import pwr.chrzescijanek.filip.fuzzyclassifier.AbstractClassifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSetStats;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Model;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Reductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Resolver;

public class TypeOneClassifier extends AbstractClassifier  {

	private TypeOneClassifier(TypeOneFuzzifier fuzzifier, Resolver resolver, Reductor reductor,
                      Defuzzifier defuzzifier) {
        super(defuzzifier, resolver, fuzzifier, reductor);
    }

    @Override
    protected Model createModel(DataSetStats stats, FuzzyDataSet fuzzyDataSet) {
        return new TypeOneModel(fuzzyDataSet, stats);
    }

    public static class Builder extends AbstractClassifier.Builder {

        public Builder(TypeOneFuzzifier fuzzifier, Resolver resolver, Reductor reductor) {
            super(fuzzifier, resolver, reductor);
        }

        @Override
        public AbstractClassifier build() {
            return new TypeOneClassifier((TypeOneFuzzifier) fuzzifier, resolver, reductor, defuzzifier);
        }

    }

}
