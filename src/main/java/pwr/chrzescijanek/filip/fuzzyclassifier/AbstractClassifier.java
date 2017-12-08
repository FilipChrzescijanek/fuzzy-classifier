package pwr.chrzescijanek.filip.fuzzyclassifier;

import java.util.Objects;
import java.util.Optional;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSetStats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Model;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.NullModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.BasicDefuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Fuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Reductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Resolver;

public abstract class AbstractClassifier implements Classifier {

    private final Fuzzifier fuzzifier;
    private final Resolver  resolver;
    private final Reductor  reductor;

    private   Defuzzifier defuzzifier;
    protected Model       model;

    protected AbstractClassifier(Defuzzifier defuzzifier, Resolver resolver,
                              Fuzzifier fuzzifier, Reductor reductor) {
        this.defuzzifier = defuzzifier;
        this.resolver    = resolver;
        this.fuzzifier   = fuzzifier;
        this.reductor    = reductor;
    }

    protected abstract Model createModel(DataSetStats stats, FuzzyDataSet fuzzyDataSet);

    public Fuzzifier getFuzzifier() {
        return fuzzifier;
    }

    public Resolver getResolver() {
        return resolver;
    }

    public Reductor getReductor() {
        return reductor;
    }

    public Defuzzifier getDefuzzifier() {
        return Optional.ofNullable(defuzzifier)
                .orElse(new BasicDefuzzifier(getModel().getClazzValues()));
    }

    public void setDefuzzifier(Defuzzifier defuzzifier) {
        this.defuzzifier = Objects.requireNonNull(defuzzifier);
    }

    public Model getModel() {
        return Optional.ofNullable(model).orElse(new NullModel());
    }

    protected void setModel(Model model) {
        this.model = model;
    }

    public Classifier train(DataSet dataSet) {
        DataSetStats stats = new DataSetStats(dataSet);
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

    public abstract static class Builder {

        protected final Fuzzifier fuzzifier;
        protected final Resolver resolver;
        protected final Reductor reductor;

        protected Defuzzifier defuzzifier;

        protected Builder(Fuzzifier fuzzifier, Resolver resolver, Reductor reductor) {
            this.fuzzifier = Objects.requireNonNull(fuzzifier);
            this.resolver  = Objects.requireNonNull(resolver);
            this.reductor  = Objects.requireNonNull(reductor);
        }

        public Builder withDefuzzifier(Defuzzifier defuzzifier) {
            this.defuzzifier = Objects.requireNonNull(defuzzifier);
            return this;
        }

        public abstract AbstractClassifier build();

    }
}
