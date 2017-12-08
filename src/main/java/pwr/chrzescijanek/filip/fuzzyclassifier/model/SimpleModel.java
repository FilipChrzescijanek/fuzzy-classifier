package pwr.chrzescijanek.filip.fuzzyclassifier.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

public class SimpleModel implements Model {

	private final List<String> classValues;
	private final List<Rule>   rules;
    private final Stats        stats;
    
	public SimpleModel(List<String> classValues, List<Rule> rules, Stats stats) {
		this.classValues = classValues;
		this.rules       = rules;
		this.stats       = stats;
	}

	@Override
	public Map<String, Double> getProbabilitiesFor(TestRecord testRecord) {
        return getRules()
                .parallelStream()
                .collect(Collectors.toMap(Rule::getClazz, rule -> rule.getProbabilityFor(testRecord, getStats())))
                .entrySet()
                .parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                / getRules()
                                .parallelStream()
                                .mapToDouble(rule -> rule.getProbabilityFor(testRecord, getStats()))
                                .sum()));
	}

	@Override
	public List<String> getClazzValues() {
        return classValues;
	}

	private List<Rule> getRules() {
		return rules;
	}

	private Stats getStats() {
		return stats;
	}

}
