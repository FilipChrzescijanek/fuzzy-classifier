package pwr.chrzescijanek.filip.fuzzyclassifier.data.raw;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Stats {

    private final Map<String, Double> means;
    private final Map<String, Double> variances;

    public Stats(DataSet dataSet) {
        this.means     = initializeMeans(Objects.requireNonNull(dataSet));
        this.variances = initializeVariances(dataSet);
    }

    public Map<String, Double> getMeans() {
        return means;
    }

    public Map<String, Double> getVariances() {
        return variances;
    }

    private Map<String, Double> initializeMeans(DataSet dataSet) {
        return Collections.unmodifiableMap(dataSet
                    .getAttributes()
                    .parallelStream()
                    .collect(
                            Collectors.toMap(
                                    Function.identity(),
                                    attribute -> dataSet
                                            .getRecords()
                                            .parallelStream()
                                            .mapToDouble(record -> record.getAttributes().get(attribute))
                                            .average().orElse(0.0))));
    }

    private Map<String, Double> initializeVariances(DataSet dataSet) {
        Integer size = dataSet.getRecords().size();
        return Collections.unmodifiableMap(dataSet
                .getAttributes()
                .parallelStream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                attribute -> dataSet
                                        .getRecords()
                                        .parallelStream()
                                        .mapToDouble(record -> record.getAttributes().get(attribute))
                                        .reduce(0.0,
                                                (acc, value) -> acc +
                                                        Math.pow(value - getMeans().get(attribute), 2) / (size - 1)))));
    }

}
