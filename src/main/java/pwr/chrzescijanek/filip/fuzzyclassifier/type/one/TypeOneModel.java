package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import java.util.Optional;

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Variable;
import com.bpodgursky.jbool_expressions.rules.RuleSet;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSetStats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Rule;

public class TypeOneModel extends AbstractModel<Double> {

	public TypeOneModel(FuzzyDataSet fuzzyDataSet, DataSetStats stats) {
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
