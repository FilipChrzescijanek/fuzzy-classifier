package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import com.bpodgursky.jbool_expressions.Expression;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;
import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzyLogic;
import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Rule extends AbstractEvaluator<String> {

    private final Expression<String> condition;
    private final String             clazz;

    public Rule(Expression<String> condition, String clazz) {
        super(AbstractModel.PARAMETERS);
        this.condition = Objects.requireNonNull(condition);
        this.clazz     = Objects.requireNonNull(clazz);
    }

    public Expression<String> getCondition() {
        return condition;
    }

    public String getClazz() {
        return clazz;
    }

    public Double getProbabilityFor(TestRecord testRecord, Stats stats) {
        return Double.parseDouble(evaluate(condition.toString(), Arrays.asList(testRecord, stats)));
    }

    protected String toValue(String literal, Object evaluationContext) {
        return getValue(literal, evaluationContext).toString();
    }

    @SuppressWarnings("unchecked")
    private Double getValue(final String literal, Object evaluationContext) {
        if (literal.contains("=")) {
            TestRecord testRecord = (TestRecord) ((List<Object>) evaluationContext).get(0);
            Stats      stats      = (Stats)      ((List<Object>) evaluationContext).get(1);

            String[] entry     = literal.split("=");
            String   attribute = entry[0];
            FuzzySet fuzzySet  = FuzzySet.valueOf(entry[1]);
            Double   value     = testRecord.getAttributes().get(attribute);

            return new FuzzyLogic()
                    .getPdfForFuzzySet(
                            fuzzySet,
                            stats.getMeans()    .get(attribute),
                            stats.getVariances().get(attribute),
                            value);
        } else {
            return Double.parseDouble(literal);
        }
    }

    protected String evaluate(Operator operator, Iterator<String> operands, Object evaluationContext) {
        final String left  = operands.next();
        final String right = operands.next();

        final Double result;
        if (operator.equals(AbstractModel.OR)) {
            result = Math.max(getValue(left, evaluationContext), getValue(right, evaluationContext));
        } else if (operator.equals(AbstractModel.AND)) {
            result = Math.min(getValue(left, evaluationContext), getValue(right, evaluationContext));
        } else {
            throw new IllegalArgumentException();
        }
        return result.toString();
    }

}
