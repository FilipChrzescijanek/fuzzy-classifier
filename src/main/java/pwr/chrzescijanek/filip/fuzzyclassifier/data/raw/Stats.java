package pwr.chrzescijanek.filip.fuzzyclassifier.data.raw;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Stats {

	protected Map<String, Double> means;
	protected Map<String, Double> variances;

	protected Stats() {}
	
	public Stats(final Map<String, Double> means, final Map<String, Double> variances) {
		this.means     = Collections.unmodifiableMap(Objects.requireNonNull(means));
		this.variances = Collections.unmodifiableMap(Objects.requireNonNull(variances));
	}

	public Map<String, Double> getMeans() {
	    return means;
	}

	public Map<String, Double> getVariances() {
	    return variances;
	}

}