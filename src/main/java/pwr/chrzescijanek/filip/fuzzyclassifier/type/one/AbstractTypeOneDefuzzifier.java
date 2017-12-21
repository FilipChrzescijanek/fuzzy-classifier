package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;

import java.util.Map;

public abstract class AbstractTypeOneDefuzzifier implements Defuzzifier<Double> {

    @Override
    public Double defuzzify(Map<String, Double> probabilities) {
        double sum = probabilities.entrySet()
                .stream()
                .mapToDouble(Map.Entry::getValue)
                .sum();
        return defuzzify(probabilities, sum);
    }

    protected abstract Double defuzzify(Map<String, Double> probabilities, double sum);

}
