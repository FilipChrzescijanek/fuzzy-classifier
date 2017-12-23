package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import java.util.Map.Entry;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzyLogic;
import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.AbstractFuzzifier;

public class TypeTwoFuzzifier extends AbstractFuzzifier {

	@Override
	protected Double getProbability(Stats stats, Entry<String, Double> e, FuzzySet s) {
		double bottom = new FuzzyLogic()
		        .getPdfForFuzzySet(s,
		                stats.getMeans().get(e.getKey()),
		                stats.narrow().getVariances().get(e.getKey()),
		                stats.widen().getVariances().get(e.getKey()),
		                e.getValue());
		double top = new FuzzyLogic()
		        .getPdfForFuzzySet(s,
		                stats.getMeans().get(e.getKey()),
		                stats.widen().getVariances().get(e.getKey()),
		                stats.narrow().getVariances().get(e.getKey()),
		                e.getValue());
		
		return (top + bottom) / 2.0;
	}
	
}
