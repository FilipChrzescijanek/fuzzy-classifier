package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzyLogic;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.AbstractFuzzifier;

public class TypeOneFuzzifier extends AbstractFuzzifier {

    @Override
    protected FuzzyLogic getFuzzyLogic() {
        return new TypeOneFuzzyLogic();
    }

}
