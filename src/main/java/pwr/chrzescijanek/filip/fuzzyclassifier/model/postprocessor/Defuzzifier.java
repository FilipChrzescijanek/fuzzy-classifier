package pwr.chrzescijanek.filip.fuzzyclassifier.model.postprocessor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Defuzzifier {

    private final Map<String, Double> sharpValues;

    public Defuzzifier(Map<String, Double> sharpValues) {
        this.sharpValues = Collections.unmodifiableMap(Objects.requireNonNull(sharpValues));
    }

    public Double defuzzify(Map<String, Double> probabilities) {
        return probabilities
                .entrySet()
                .stream()
                .mapToDouble(e -> sharpValues.get(e.getKey()) * e.getValue())
                .sum();
    }

}
