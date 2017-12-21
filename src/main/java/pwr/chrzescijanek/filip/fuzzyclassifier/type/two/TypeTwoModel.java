package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Variable;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Rule;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TypeTwoModel extends AbstractModel<Range> {

    public TypeTwoModel(FuzzyDataSet fuzzyDataSet, Stats stats) {
        super(stats, fuzzyDataSet);
    }

    @Override
    protected Rule buildRule(String clazz, Expression<String> condition) {
        return new Rule(RuleSet
                .simplify(Optional.ofNullable(condition).orElse(Variable.of("0.0"))), clazz);
    }

    @Override
    protected Range getProbabilityFor(TestRecord testRecord, Rule rule) {
        Map<String, Double> bottomVariances = multiplyVariances(0.9);
        Map<String, Double> topVariances = multiplyVariances(1.1);
        Map<String, Double> means = getStats().getMeans();

        Stats bottomStats = new Stats(means, bottomVariances);
        Stats topStats = new Stats(means, topVariances);
        return new Range(rule.getProbabilityFor(testRecord, bottomStats),
                rule.getProbabilityFor(testRecord, topStats));
    }

    private Map<String, Double> multiplyVariances(double factor) {
        return getStats().getVariances()
                .entrySet()
                .parallelStream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue() * factor
                    ));
    }

}
