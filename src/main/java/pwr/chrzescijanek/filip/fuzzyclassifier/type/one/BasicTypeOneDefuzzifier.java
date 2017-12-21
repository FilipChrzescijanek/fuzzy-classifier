package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;

import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BasicTypeOneDefuzzifier extends AbstractTypeOneDefuzzifier {
	
    private final List<String> clazzValues;

    public BasicTypeOneDefuzzifier(List<String> clazzValues) {
        this.clazzValues = Collections.unmodifiableList(Objects.requireNonNull(clazzValues));
    }

    public List<String> getClazzValues() {
        return clazzValues;
    }

    @Override
    public Double defuzzify(Map<String, Double> probabilities, double sum) {
        return probabilities
                .entrySet()
                .parallelStream()
                .mapToDouble(e -> getClazzValues().indexOf(e.getKey()) * e.getValue() / sum)
                .sum();
    }

}
