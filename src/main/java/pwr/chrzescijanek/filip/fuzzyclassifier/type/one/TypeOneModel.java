package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import com.bpodgursky.jbool_expressions.*;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.Rule;

import java.util.*;

public class TypeOneModel extends AbstractModel {

    public TypeOneModel(FuzzyDataSet fuzzyDataSet, Stats stats) {
        super(stats, fuzzyDataSet);
    }

    @Override
    protected Rule buildRule(String clazz, Expression<String> condition) {
        return new TypeOneRule(clazz, RuleSet
                .simplify(Optional.ofNullable(condition).orElse(Variable.of("0.0"))));
    }

}
