package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import com.bpodgursky.jbool_expressions.Expression;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzyLogic;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractModel;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.AbstractRule;

public class TypeOneRule extends AbstractRule {

	public TypeOneRule(String clazz, Expression<String> condition) {
        super(AbstractModel.PARAMETERS, condition, clazz);
    }

    @Override
    protected FuzzyLogic getFuzzyLogic() {
        return new TypeOneFuzzyLogic();
    }

}
