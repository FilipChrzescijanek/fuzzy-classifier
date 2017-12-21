package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import com.bpodgursky.jbool_expressions.*;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Rule;

import java.util.*;

public class TypeOneModel extends AbstractModel<Double> {

    public TypeOneModel(FuzzyDataSet fuzzyDataSet, Stats stats) {
        super(stats, fuzzyDataSet);
    }

    @Override
    protected Rule buildRule(String clazz, Expression<String> condition) {
        return new Rule(RuleSet
                .simplify(Optional.ofNullable(condition).orElse(Variable.of("0.0"))), clazz);
    }

    @Override
    protected Double getProbabilityFor(TestRecord testRecord, Rule rule) {
        return rule.getProbabilityFor(testRecord, getStats());
    }

}
