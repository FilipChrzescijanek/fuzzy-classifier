package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import pwr.chrzescijanek.filip.fuzzyclassifier.Classifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;

public class SimpleTypeOneClassifier implements Classifier {

	private final TypeOneModel model;
	private final Defuzzifier<Double> defuzzifier;
	
	public SimpleTypeOneClassifier(TypeOneModel model, Defuzzifier<Double> defuzzifier) {
		this.model       = model;
		this.defuzzifier = defuzzifier;
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

	private TypeOneModel getModel() {
		return model;
	}

	private Defuzzifier<Double> getDefuzzifier() {
		return defuzzifier;
	}

}
