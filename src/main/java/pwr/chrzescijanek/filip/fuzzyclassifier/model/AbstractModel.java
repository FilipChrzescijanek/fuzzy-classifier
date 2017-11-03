package pwr.chrzescijanek.filip.fuzzyclassifier.model;

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
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private final List<Rule> rules;
    private final Stats      stats;

    protected AbstractModel(Stats stats, FuzzyDataSet fuzzyDataSet) {
        this.stats = Objects.requireNonNull(stats);
        this.rules = createRules(fuzzyDataSet);
    }

    protected abstract Rule buildRule(String clazz, Expression<String> condition);

    public List<Rule> getRules() {
        return rules;
    }

    public Stats getStats() {
        return stats;
    }

    @Override
    public Map<String, Double> getProbabilitiesFor(TestRecord testRecord) {
        return getRules()
                .stream()
                .collect(Collectors.toMap(Rule::getClazz, rule -> rule.getProbabilityFor(testRecord, getStats())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                / getRules()
                                .stream()
                                .mapToDouble(rule -> rule.getProbabilityFor(testRecord, getStats()))
                                .sum()));
    }

    @Override
    public List<String> getClazzValues() {
        return getRules()
                .stream()
                .map(Rule::getClazz).collect(Collectors.toList());
    }

    private List<Rule> createRules(FuzzyDataSet fuzzyDataSet) {
        Map<String, List<FuzzyRecord>> distinctRecords = fuzzyDataSet
                .getRecords()
                .stream()
                .distinct()
                .collect(Collectors.groupingBy(FuzzyRecord::getClazz));

        return fuzzyDataSet
                .getClazzValues()
                .stream()
                .map(clazz -> buildRule(distinctRecords, clazz))
                .collect(Collectors.toList());
    }

    private Rule buildRule(Map<String, List<FuzzyRecord>> distinctRecords, String clazz) {
        Expression<String> alternative = null;
        for (FuzzyRecord fr : distinctRecords.get(clazz)) {
            Expression<String> conjunction = null;
            for (Map.Entry<String, FuzzySet> entry : fr.getAttributes().entrySet()) {
                Expression<String> variable = Variable.of(entry.toString());
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
