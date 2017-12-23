package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import java.util.Map.Entry;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzyLogic;
import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.AbstractFuzzifier;

public class TypeOneFuzzifier extends AbstractFuzzifier {

	@Override
	protected Double getProbability(Stats stats, Entry<String, Double> e, FuzzySet s) {
		return new FuzzyLogic()
		        .getPdfForFuzzySet(s,
		                stats.getMeans().get(e.getKey()),
		                stats.getVariances().get(e.getKey()),
		                stats.getVariances().get(e.getKey()),
		                e.getValue());
	}
	
}
