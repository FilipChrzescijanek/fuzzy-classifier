package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import java.util.List;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Rule;
public class TypeTwoModel extends AbstractModel<Range> {
	
    public TypeTwoModel(FuzzyDataSet fuzzyDataSet, Stats stats) {
        super(stats, fuzzyDataSet);
    }

    public TypeTwoModel(List<Rule> rules, List<String> classValues, Stats stats) {
        super(rules, classValues, stats);
    }

    @Override
    protected Range getProbabilityFor(TestRecord testRecord, Rule rule) {
        return new Range(rule.getProbabilityFor(testRecord, getStats().narrow(), getStats().widen()),
                rule.getProbabilityFor(testRecord, getStats().widen(), getStats().narrow()));
    }

}
