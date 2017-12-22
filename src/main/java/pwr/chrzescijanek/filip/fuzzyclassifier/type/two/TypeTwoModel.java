package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import java.util.List;
import java.util.Optional;

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Variable;
import com.bpodgursky.jbool_expressions.rules.RuleSet;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Rule;

import java.util.Map;
import java.util.stream.Collectors;
public class TypeTwoModel extends AbstractModel<Range> {

    private final Stats bottomStats;
    private final Stats topStats;

    public TypeTwoModel(FuzzyDataSet fuzzyDataSet, Stats stats) {
        super(stats, fuzzyDataSet);
        Map<String, Double> means = getStats().getMeans();
        Map<String, Double> bottomVariances = multiplyVariances(0.9);
        Map<String, Double> topVariances = multiplyVariances(1.1);

        bottomStats = new Stats(means, bottomVariances);
        topStats = new Stats(means, topVariances);
    }

    public TypeTwoModel(List<Rule> rules, List<String> classValues, Stats stats) {
        super(rules, classValues, stats);
        Map<String, Double> means = getStats().getMeans();
        Map<String, Double> bottomVariances = multiplyVariances(0.9);
        Map<String, Double> topVariances = multiplyVariances(1.1);

        bottomStats = new Stats(means, bottomVariances);
        topStats = new Stats(means, topVariances);
    }

    @Override
    protected Rule buildRule(String clazz, Expression<String> condition) {
        return new Rule(RuleSet
                .simplify(Optional.ofNullable(condition).orElse(Variable.of("0.0"))), clazz);
    }

    @Override
    protected Range getProbabilityFor(TestRecord testRecord, Rule rule) {
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
