package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import pwr.chrzescijanek.filip.fuzzyclassifier.Classifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;

public class SimpleClassifier implements Classifier {

	private final SimpleModel model;
	private final Defuzzifier        defuzzifier;
	
	public SimpleClassifier(SimpleModel model, Defuzzifier defuzzifier) {
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

	private SimpleModel getModel() {
		return model;
	}

	private Defuzzifier getDefuzzifier() {
		return defuzzifier;
	}

}
