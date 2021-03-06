package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import java.util.Optional;

import pwr.chrzescijanek.filip.fuzzyclassifier.AbstractClassifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Model;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.NullModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Reductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Resolver;

public class TypeOneClassifier extends AbstractClassifier<Double> {

    private TypeOneClassifier(TypeOneFuzzifier fuzzifier, Resolver resolver, Reductor reductor,
                      Defuzzifier<Double> defuzzifier) {
        super(defuzzifier, resolver, fuzzifier, reductor);
    }

    @Override
    protected Model<Double> createModel(Stats stats, FuzzyDataSet fuzzyDataSet) {
        return new TypeOneModel(fuzzyDataSet, stats);
    }

    @Override
    public Defuzzifier<Double> getDefuzzifier() {
        return Optional.ofNullable(defuzzifier)
                .orElse(new BasicTypeOneDefuzzifier(getModel().getClazzValues()));
    }

    @Override
    public Model<Double> getModel() {
        return Optional.ofNullable(model).orElse(new NullModel<>());
    }

    public static class Builder extends AbstractClassifier.Builder<Double> {

        public Builder(TypeOneFuzzifier fuzzifier, Resolver resolver, Reductor reductor) {
            super(fuzzifier, resolver, reductor);
        }

        @Override
        public AbstractClassifier<Double> build() {
            return new TypeOneClassifier((TypeOneFuzzifier) fuzzifier, resolver, reductor, defuzzifier);
        }

    }

}
