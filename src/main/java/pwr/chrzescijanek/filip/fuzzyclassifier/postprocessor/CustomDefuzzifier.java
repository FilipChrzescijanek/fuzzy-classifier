package pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class CustomDefuzzifier implements Defuzzifier {

	private final Map<String, Double> sharpValues;

    public CustomDefuzzifier(Map<String, Double> sharpValues) {
        this.sharpValues = Collections.unmodifiableMap(Objects.requireNonNull(sharpValues));
    }

    @Override
    public Double defuzzify(Map<String, Double> probabilities) {
        return probabilities
                .entrySet()
                .parallelStream()
                .mapToDouble(e -> sharpValues.get(e.getKey()) * e.getValue())
                .sum();
    }

	public Map<String, Double> getSharpValues() {
		return sharpValues;
	}

}
