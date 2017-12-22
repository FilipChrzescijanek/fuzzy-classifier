package pwr.chrzescijanek.filip.fuzzyclassifier;

import java.util.Objects;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Model;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Fuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Reductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Resolver;

public abstract class AbstractClassifier<T> implements Classifier {

    private final Fuzzifier fuzzifier;
    private final Resolver  resolver;
    private final Reductor  reductor;

    protected Defuzzifier<T> defuzzifier;
    protected Model<T>       model;

    protected AbstractClassifier(Defuzzifier<T> defuzzifier, Resolver resolver,
                              Fuzzifier fuzzifier, Reductor reductor) {
        this.defuzzifier = defuzzifier;
        this.resolver    = resolver;
        this.fuzzifier   = fuzzifier;
        this.reductor    = reductor;
    }

    protected abstract Model<T> createModel(Stats stats, FuzzyDataSet fuzzyDataSet);

    public Fuzzifier getFuzzifier() {
        return fuzzifier;
    }

    public Resolver getResolver() {
        return resolver;
    }

    public Reductor getReductor() {
        return reductor;
    }

    public abstract Defuzzifier<T> getDefuzzifier();

    public abstract Model<T> getModel();

    public void setDefuzzifier(Defuzzifier<T> defuzzifier) {
        this.defuzzifier = Objects.requireNonNull(defuzzifier);
    }

    protected void setModel(Model<T> model) {
        this.model = model;
    }

    public Classifier train(DataSet dataSet) {
        Stats stats = new Stats(dataSet);
        FuzzyDataSet fuzzyDataSet = getReductor()
                .reduce(getResolver()
                        .resolve(getFuzzifier()
                                .fuzzify(dataSet, stats)));

        setModel(createModel(stats, fuzzyDataSet));
        return this;
    }

    @Override
    public void test(TestDataSet dataSet) {
    	dataSet
                .getTestRecords()
                .forEach(testRecord ->
                        testRecord.setValue(
                                getDefuzzifier()
                                        .defuzzify(getModel().getProbabilitiesFor(testRecord))));
    }

    public abstract static class Builder<T> {

        protected final Fuzzifier fuzzifier;
        protected final Resolver resolver;
        protected final Reductor reductor;

        protected Defuzzifier<T> defuzzifier;

        protected Builder(Fuzzifier fuzzifier, Resolver resolver, Reductor reductor) {
            this.fuzzifier = Objects.requireNonNull(fuzzifier);
            this.resolver  = Objects.requireNonNull(resolver);
            this.reductor  = Objects.requireNonNull(reductor);
        }

        public Builder<T> withDefuzzifier(Defuzzifier<T> defuzzifier) {
            this.defuzzifier = Objects.requireNonNull(defuzzifier);
            return this;
        }

        public abstract AbstractClassifier<T> build();

    }
}
