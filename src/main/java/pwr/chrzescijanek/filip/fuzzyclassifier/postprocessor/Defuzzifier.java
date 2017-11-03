package pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor;

import java.util.Map;

public interface Defuzzifier {

    Double defuzzify(Map<String, Double> probabilities);

}
