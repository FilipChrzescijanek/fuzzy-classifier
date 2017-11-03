package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import com.bpodgursky.jbool_expressions.Expression;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.Operator;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.common.NormalDistribution;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Rule extends AbstractEvaluator<String> {

    private final String             clazz;
    private final Expression<String> condition;

    public Rule(String clazz, Expression<String> condition) {
        super(Model.PARAMETERS);

        this.clazz     = Objects.requireNonNull(clazz);
        this.condition = Objects.requireNonNull(condition);
    }

    public Double getProbabilityFor(TestRecord testRecord, Stats stats) {
        return Double.parseDouble(evaluate(condition.toString(), Arrays.asList(testRecord, stats)));
    }

    public String getClazz() {
        return clazz;
    }

    @Override
    protected String toValue(String literal, Object evaluationContext) {
        return getValue(literal, evaluationContext).toString();
    }

    private Double getValue(final String literal, Object evaluationContext) {
        if (literal.contains("=")) {
            TestRecord testRecord = (TestRecord) ((List) evaluationContext).get(0);
            Stats      stats      = (Stats)      ((List) evaluationContext).get(1);

            String[] entry     = literal.split("=");
            String   attribute = entry[0];
            FuzzySet fuzzySet  = FuzzySet.valueOf(entry[1]);
            Double   value     = testRecord.getAttributes().get(attribute);

            return new NormalDistribution()
                    .getPdfForFuzzySet(
                            fuzzySet,
                            stats.getMeans()    .get(attribute),
                            stats.getVariances().get(attribute),
                            value);
        } else {
            return Double.parseDouble(literal);
        }
    }

    @Override
    protected String evaluate(Operator operator, Iterator<String> operands, Object evaluationContext) {
        final String left  = operands.next();
        final String right = operands.next();

        final Double result;
        if (operator.equals(Model.OR)) {
            result = Math.max(getValue(left, evaluationContext), getValue(right, evaluationContext));
        } else if (operator.equals(Model.AND)) {
            result = Math.min(getValue(left, evaluationContext), getValue(right, evaluationContext));
        } else {
            throw new IllegalArgumentException();
        }
        return result.toString();
    }

}
