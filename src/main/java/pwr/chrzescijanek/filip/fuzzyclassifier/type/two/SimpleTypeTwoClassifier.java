package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import pwr.chrzescijanek.filip.fuzzyclassifier.Classifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;

public class SimpleTypeTwoClassifier implements Classifier {

	private final TypeTwoModel model;
	private final Defuzzifier<Range> defuzzifier;

	public SimpleTypeTwoClassifier(TypeTwoModel model, Defuzzifier<Range> defuzzifier) {
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

	private TypeTwoModel getModel() {
		return model;
	}

	private Defuzzifier<Range> getDefuzzifier() {
		return defuzzifier;
	}

}
