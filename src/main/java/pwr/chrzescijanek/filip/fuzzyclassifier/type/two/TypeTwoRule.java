package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import com.bpodgursky.jbool_expressions.Expression;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzyLogic;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractRule;

public class TypeTwoRule extends AbstractRule {

    public TypeTwoRule(String clazz, Expression<String> condition) {
        super(AbstractModel.PARAMETERS, condition, clazz);
    }

    @Override
    protected FuzzyLogic getFuzzyLogic() {
        return new TypeTwoFuzzyLogic();
    }

}
