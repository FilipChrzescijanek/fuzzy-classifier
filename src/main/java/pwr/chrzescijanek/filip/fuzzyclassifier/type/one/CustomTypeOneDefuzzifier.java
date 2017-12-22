package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class CustomTypeOneDefuzzifier extends AbstractTypeOneDefuzzifier {

    private final Map<String, Double> sharpValues;

    public CustomTypeOneDefuzzifier(Map<String, Double> sharpValues) {
        this.sharpValues = Collections.unmodifiableMap(Objects.requireNonNull(sharpValues));
    }

    public Map<String, Double> getSharpValues() {
        return sharpValues;
    }

    @Override
    protected Double defuzzify(Map<String, Double> probabilities, double sum) {
        return probabilities
                .entrySet()
                .stream()
                .mapToDouble(e -> getSharpValues().get(e.getKey()) * e.getValue() / sum)
                .sum();
    }

}
