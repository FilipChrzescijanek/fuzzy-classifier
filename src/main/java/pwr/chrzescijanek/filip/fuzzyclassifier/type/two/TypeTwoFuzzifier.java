package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzyLogic;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.AbstractFuzzifier;

public class TypeTwoFuzzifier extends AbstractFuzzifier {

    @Override
    protected FuzzyLogic getFuzzyLogic() {
        return new TypeTwoFuzzyLogic();
    }

}
