package pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor;

import java.util.Map;

public interface Defuzzifier<T> {

    Double defuzzify(Map<String, T> probabilities);

}
