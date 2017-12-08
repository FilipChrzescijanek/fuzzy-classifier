package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.bpodgursky.jbool_expressions.And;
import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Or;
import com.bpodgursky.jbool_expressions.Variable;
import com.fathzer.soft.javaluator.BracketPair;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSetStats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

public abstract class AbstractModel implements Model {

    public static final Operator AND = new Operator("&", 2, Operator.Associativity.LEFT, 2);
    public static final Operator OR  = new Operator("|", 2, Operator.Associativity.LEFT, 1);

    public static final Parameters PARAMETERS;

    static {
        PARAMETERS = new Parameters();
        PARAMETERS.add(AND);
        PARAMETERS.add(OR);
        PARAMETERS.addExpressionBracket(BracketPair.PARENTHESES);
    }

    private final List<Rule>   rules;
    private final List<String> classValues;
    private final Stats        stats;

    protected AbstractModel(DataSetStats stats, FuzzyDataSet fuzzyDataSet) {
        this.rules       = Collections.unmodifiableList(createRules(fuzzyDataSet));
        this.classValues = Collections.unmodifiableList(fuzzyDataSet.getClazzValues());
        this.stats       = Objects.requireNonNull(stats);
    }

    protected abstract Rule buildRule(String clazz, Expression<String> condition);

    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public List<String> getClazzValues() {
        return classValues;
    }

    public Stats getStats() {
        return stats;
    }

    @Override
    public Map<String, Double> getProbabilitiesFor(TestRecord testRecord) {
        return getRules()
                .parallelStream()
                .collect(Collectors.toMap(Rule::getClazz, rule -> rule.getProbabilityFor(testRecord, getStats())))
                .entrySet()
                .parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                / getRules()
                                .parallelStream()
                                .mapToDouble(rule -> rule.getProbabilityFor(testRecord, getStats()))
                                .sum()));
    }

    private List<Rule> createRules(FuzzyDataSet fuzzyDataSet) {
        Map<String, List<FuzzyRecord>> distinctRecords = fuzzyDataSet
                .getRecords()
                .parallelStream()
                .distinct()
                .collect(Collectors.groupingBy(FuzzyRecord::getClazz));

        return distinctRecords
                .keySet()
                .parallelStream()
                .map(clazz -> buildRule(distinctRecords, clazz))
                .collect(Collectors.toList());
    }

    private Rule buildRule(Map<String, List<FuzzyRecord>> distinctRecords, String clazz) {
        Expression<String> alternative = null;
        for (FuzzyRecord fr : distinctRecords.get(clazz)) {
            Expression<String> conjunction = null;
            for (Map.Entry<String, FuzzySet> entry : fr.getAttributes().entrySet()) {
                Expression<String> variable = Variable.of(entry.toString().replace('=', '_'));
                if (conjunction == null) {
                    conjunction = variable;
                } else {
                    conjunction = And.of(conjunction, variable);
                }
            }
            if (alternative == null && conjunction != null) {
                alternative = conjunction;
            } else if (conjunction != null) {
                alternative = Or.of(alternative, conjunction);
            }
        }
        return buildRule(clazz, alternative);
    }

}
