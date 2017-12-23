package pwr.chrzescijanek.filip.fuzzyclassifier.data.raw;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Stats {

	private final Map<String, Double> means;
	private final Map<String, Double> variances;
    private Map<String, Double> widerVariances;
    private Map<String, Double> narrowerVariances;


	public Stats(DataSet dataSet) {
		this.means     = initializeMeans(Objects.requireNonNull(dataSet));
		this.variances = initializeVariances(dataSet);
	}

	public Stats(final Map<String, Double> means, final Map<String, Double> variances) {
		this.means     = Collections.unmodifiableMap(Objects.requireNonNull(means));
		this.variances = Collections.unmodifiableMap(Objects.requireNonNull(variances));
	}
	
	public Stats widen() {
		if (getWiderVariances() == null) {
			setWiderVariances(Collections.unmodifiableMap(multiplyVariances(1.21)));
		}
		return new Stats(getMeans(), getWiderVariances());
	}
	
	public Stats narrow() {
		if (getNarrowerVariances() == null) {
			setNarrowerVariances(Collections.unmodifiableMap(multiplyVariances(0.81)));
		}
		return new Stats(getMeans(), getNarrowerVariances());
	}

	public Map<String, Double> getMeans() {
		return means;
	}

	public Map<String, Double> getVariances() {
		return variances;
	}

	public Map<String, Double> getWiderVariances() {
		return widerVariances;
	}

	public Map<String, Double> getNarrowerVariances() {
		return narrowerVariances;
	}

	public void setWiderVariances(Map<String, Double> widerVariances) {
		this.widerVariances = widerVariances;
	}

	public void setNarrowerVariances(Map<String, Double> narrowerVariances) {
		this.narrowerVariances = narrowerVariances;
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
										.stream()
										.mapToDouble(record -> record.getAttributes().get(attribute))
										.reduce(0.0,
												(acc, value) -> acc +
														Math.pow(value - getMeans().get(attribute), 2) / (size - 1)))));
	}

	private Map<String, Double> multiplyVariances(double factor) {
        return getVariances()
                .entrySet()
                .parallelStream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue() * factor
                    ));
    }


}