package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Variable;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Rule;

import java.util.Optional;

public class TypeTwoModel extends AbstractModel {

    public TypeTwoModel(FuzzyDataSet fuzzyDataSet, Stats stats) {
        super(stats, fuzzyDataSet);
    }

    @Override
    protected Rule buildRule(String clazz, Expression<String> condition) {
        return new TypeTwoRule(clazz, RuleSet
                .simplify(Optional.ofNullable(condition).orElse(Variable.of("0.0"))));
    }

}