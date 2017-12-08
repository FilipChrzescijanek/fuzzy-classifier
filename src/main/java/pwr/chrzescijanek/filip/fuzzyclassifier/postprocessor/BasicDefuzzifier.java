package pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BasicDefuzzifier implements Defuzzifier {
	
	private List<String> clazzValues;

    public BasicDefuzzifier(List<String> clazzValues) {
        this.clazzValues = Collections.unmodifiableList(Objects.requireNonNull(clazzValues));
    }

    public List<String> getClazzValues() {
        return clazzValues;
    }

    @Override
    public Double defuzzify(Map<String, Double> probabilities) {
        return probabilities
                .entrySet()
                .parallelStream()
                .mapToDouble(e -> getClazzValues().indexOf(e.getKey()) * e.getValue())
                .sum();
    }

}
