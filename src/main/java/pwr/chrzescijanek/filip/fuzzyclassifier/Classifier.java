package pwr.chrzescijanek.filip.fuzzyclassifier;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.*;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.postprocessor.BasicDefuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.postprocessor.Defuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.preprocessor.ConflictResolver;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.preprocessor.Fuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.preprocessor.Reductor;

public class Classifier {

    private Model model;

    public Classifier train(DataSet dataSet) {
        Stats stats = new Stats(dataSet);
        FuzzyDataSet fuzzyDataSet = new Reductor()
                .reduce(new ConflictResolver()
                        .resolve(new Fuzzifier()
                                .fuzzify(dataSet, stats)));

        this.model = new Model(fuzzyDataSet, stats);
        return this;
    }

    public void test(TestDataSet dataSet) {
        test(dataSet, new BasicDefuzzifier(model.getClazzValues()));
    }

    public void test(TestDataSet testDataSet, Defuzzifier defuzzifier) {
        testDataSet
                .getTestRecords()
                .forEach(testRecord -> testRecord.setValue(defuzzifier.defuzzify(model.getProbabilitiesFor(testRecord))));
    }

}
