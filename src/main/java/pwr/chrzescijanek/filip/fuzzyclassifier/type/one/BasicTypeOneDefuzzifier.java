package pwr.chrzescijanek.filip.fuzzyclassifier.type.one;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BasicTypeOneDefuzzifier extends AbstractTypeOneDefuzzifier {
	
    private final List<String> clazzValues;

    public BasicTypeOneDefuzzifier(List<String> clazzValues) {
        this.clazzValues = Collections.unmodifiableList(Objects.requireNonNull(clazzValues));
    }

    public List<String> getClazzValues() {
        return clazzValues;
    }

    @Override
    protected Double defuzzify(Map<String, Double> probabilities, double sum) {
        return probabilities
                .entrySet()
                .parallelStream()
                .mapToDouble(e -> getClazzValues().indexOf(e.getKey()) * e.getValue() / sum)
                .sum();
    }

}
