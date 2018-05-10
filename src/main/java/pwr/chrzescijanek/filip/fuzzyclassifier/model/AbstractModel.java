package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bpodgursky.jbool_expressions.And;
import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Or;
import com.bpodgursky.jbool_expressions.Variable;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import com.fathzer.soft.javaluator.BracketPair;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

public abstract class AbstractModel<T> implements Model<T> {

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

    protected AbstractModel(Stats stats, FuzzyDataSet fuzzyDataSet) {
        this.classValues = Collections.unmodifiableList(fuzzyDataSet.getClazzValues());
        this.rules       = Collections.unmodifiableList(createRules(fuzzyDataSet));
        this.stats       = Objects.requireNonNull(stats);
    }

    protected AbstractModel(List<Rule> rules, List<String> classValues, Stats stats) {
        this.classValues = Collections.unmodifiableList(Objects.requireNonNull(classValues));
        this.rules       = Collections.unmodifiableList(Objects.requireNonNull(rules));
        this.stats       = Objects.requireNonNull(stats);
    }

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
    public Map<String, T> getProbabilitiesFor(TestRecord testRecord) {
        return getRules()
                .parallelStream()
                .collect(
                        Collectors.toMap(Rule::getClazz, rule -> getProbabilityFor(testRecord, rule)));
    }

    protected abstract T getProbabilityFor(TestRecord testRecord, Rule rule);

    private List<Rule> createRules(FuzzyDataSet fuzzyDataSet) {
    	Map<FuzzyRecord, List<List<String>>> differences = getDifferences(fuzzyDataSet);
    	
        return getClazzValues()
                .parallelStream()
                .map(clazz -> buildRule(fuzzyDataSet, differences, clazz))
                .collect(Collectors.toList());
    }

    private Rule buildRule(FuzzyDataSet fuzzyDataSet, Map<FuzzyRecord, List<List<String>>> differences, 
    		String clazz) {
        Expression<String> alternative = null;
        List<FuzzyRecord> clazzRecords = fuzzyDataSet.getRecords().parallelStream()
        		.filter(r -> r.getClazz().equals(clazz))
        		.collect(Collectors.toList());
		for (FuzzyRecord fr : clazzRecords) {
            Expression<String> conjunction = getImplicants(differences, fr);
            
            if (alternative == null && conjunction != null) {
                alternative = conjunction;
            } else if (conjunction != null) {
                alternative = Or.of(alternative, conjunction);
            }
        }
        return buildRule(clazz, alternative);
    }

    private Rule buildRule(String clazz, Expression<String> condition) {
        return new Rule(RuleSet
                .simplify(Optional.ofNullable(condition).orElse(Variable.of("0"))), clazz);
    }
    
    private Expression<String> getImplicants(Map<FuzzyRecord, List<List<String>>> differences, FuzzyRecord fr) {
        Expression<String> conjunction = null;
    	for (List<String> diff : differences.get(fr)) {
            Expression<String> alternative = null;
        	for (String a : diff) {
                Expression<String> variable = Variable.of(a + "_" + fr.getAttributes().get(a));

            	if (alternative == null) {
            		alternative = variable;
                } else {
                	alternative = Or.of(alternative, variable);
                }
        	}

            if (conjunction == null && alternative != null) {
                conjunction = alternative;
            } else if (alternative != null) {
                conjunction = And.of(conjunction, alternative);
            }
        }
    	return conjunction == null ? null : RuleSet.simplify(conjunction);
    }

    private Map<FuzzyRecord, List<List<String>>> getDifferences(FuzzyDataSet dataSet) {
    	List<FuzzyRecord> records = dataSet.getRecords();
    	Map<FuzzyRecord, List<List<String>>> differences = new HashMap<>();

        for (int i = 0; i < records.size(); i++) {
        	final FuzzyRecord first = records.get(i);
        	differences.put(first, new ArrayList<>());
            for (int j = 0; j < records.size(); j++) {
                final FuzzyRecord second = records.get(j);
                if (!first.getClazz().equals(second.getClazz())) {
	                List<String> difference = dataSet.getAttributes()
	                        .parallelStream()
	                        .filter(attribute ->
	                                !first.getAttributes().get(attribute)
	                                        .equals(second.getAttributes().get(attribute)))
	                        .collect(Collectors.toList());
	                if (!difference.isEmpty()) {
	                	differences.get(first).add(difference);
	                }
                }
            }
        }

        return differences;
    }

}
