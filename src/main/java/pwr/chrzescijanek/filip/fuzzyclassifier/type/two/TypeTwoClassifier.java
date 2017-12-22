package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import pwr.chrzescijanek.filip.fuzzyclassifier.AbstractClassifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Model;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.NullModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Fuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Reductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Resolver;

import java.util.Optional;

public class TypeTwoClassifier extends AbstractClassifier<Range> {

    private TypeTwoClassifier(Fuzzifier fuzzifier, Resolver resolver, Reductor reductor,
                              Defuzzifier defuzzifier) {
        super(defuzzifier, resolver, fuzzifier, reductor);
    }

    @Override
    protected Model<Range> createModel(Stats stats, FuzzyDataSet fuzzyDataSet) {
        return new TypeTwoModel(fuzzyDataSet, stats);
    }

    @Override
    public Defuzzifier<Range> getDefuzzifier() {
        return Optional.ofNullable(defuzzifier)
                .orElse(new BasicTypeTwoDefuzzifier(getModel().getClazzValues()));
    }

    @Override
    public Model<Range> getModel() {
        return Optional.ofNullable(model).orElse(new NullModel<>());
    }

    public static class Builder extends AbstractClassifier.Builder<Range> {

        public Builder(Fuzzifier fuzzifier, Resolver resolver, Reductor reductor) {
            super(fuzzifier, resolver, reductor);
        }

        @Override
        public AbstractClassifier build() {
            return new TypeTwoClassifier(fuzzifier, resolver, reductor, defuzzifier);
        }

    }

}
