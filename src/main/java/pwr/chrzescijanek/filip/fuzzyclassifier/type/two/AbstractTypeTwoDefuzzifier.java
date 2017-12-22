package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.Defuzzifier;

public abstract class AbstractTypeTwoDefuzzifier implements Defuzzifier<Range> {

    private static final double EPSILON = Math.pow(10, -9);

    private final Map<String, Double> bottomValues;
    private final Map<String, Double> topValues;

    public AbstractTypeTwoDefuzzifier(Map<String, Double> bottomValues, Map<String, Double> topValues) {
        this.bottomValues = Collections.unmodifiableMap(Objects.requireNonNull(bottomValues));
        this.topValues = Collections.unmodifiableMap(Objects.requireNonNull(topValues));
    }

    public Map<String, Double> getBottomValues() {
        return bottomValues;
    }

    public Map<String, Double> getTopValues() {
        return topValues;
    }

    @Override
    public Double defuzzify(Map<String, Range> probabilities) {
        double left = getY(probabilities, getBottomValues(), true);
        double right = getY(probabilities, getTopValues(), false);

        return (left + right) / 2.0;
    }

    private double getY(Map<String, Range> probabilities, Map<String, Double> sharpValues, boolean left) {
        List<Map.Entry<String, Double>> sorted = sortByValues(sharpValues);

        Double y2 = null;
        double y = calculateY(sorted, initializeWithAverages(probabilities, sorted));

        do {
            if (y2 != null) {
                y = y2;
            }

            final double currentY = y;
            long k = sorted.stream().filter(it -> it.getValue() <= currentY).count();

            y2 = calculateY(sorted, left ?
                            getAssignmentForLeft(probabilities, sorted, k)
                            : getAssignmentForRight(probabilities, sorted, k));
        }

        while (Math.abs(y2 - y) > EPSILON);

        return y;
    }

    private List<Map.Entry<String, Double>> sortByValues(Map<String, Double> map) {
        return map.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());
    }

    private List<Double> initializeWithAverages(Map<String, Range> probabilities, List<Map.Entry<String, Double>> sorted) {
        return sorted.stream()
                .map(e -> {
                    Range range = probabilities.get(e.getKey());
                    return (range.getLeft() + range.getRight()) / 2.0;
                })
                .collect(Collectors.toList());
    }

    private double calculateY(List<Map.Entry<String, Double>> sorted, List<Double> fs) {
        double sumOfFs = fs.stream().mapToDouble(Double::doubleValue).sum();
        double sum = 0.0;
        for (int i = 0; i < sorted.size(); i++) {
            sum += sorted.get(i).getValue() * fs.get(i);
        }
        return sum / sumOfFs;
    }

    private List<Double> getAssignmentForLeft(Map<String, Range> probabilities, List<Map.Entry<String, Double>> sorted, long k) {
        List<Double> fs = new ArrayList<>();

        for (int i = 0; i < sorted.size(); i++) {
            if (i < k) {
                fs.add(probabilities.get(sorted.get(i).getKey()).getRight());
            } else {
                fs.add(probabilities.get(sorted.get(i).getKey()).getLeft());
            }
        }

        return fs;
    }

    private List<Double> getAssignmentForRight(Map<String, Range> probabilities, List<Map.Entry<String, Double>> sorted, long k) {
        List<Double> fs = new ArrayList<>();

        for (int i = 0; i < sorted.size(); i++) {
            if (i < k) {
                fs.add(probabilities.get(sorted.get(i).getKey()).getLeft());
            } else {
                fs.add(probabilities.get(sorted.get(i).getKey()).getRight());
            }
        }

        return fs;
    }

}
