package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import java.util.List;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Rule;

public class TypeOneModel extends AbstractModel<Double> {

	public TypeOneModel(FuzzyDataSet fuzzyDataSet, Stats stats) {
        super(stats, fuzzyDataSet);
    }

    public TypeOneModel(List<Rule> rules, List<String> classValues, Stats stats) {
	    super(rules, classValues, stats);
    }

    @Override
    protected Double getProbabilityFor(TestRecord testRecord, Rule rule) {
        return rule.getProbabilityFor(testRecord, getStats());
    }

}
