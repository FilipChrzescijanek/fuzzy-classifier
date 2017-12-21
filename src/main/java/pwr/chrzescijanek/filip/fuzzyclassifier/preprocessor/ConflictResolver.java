package pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyRecord;

public class ConflictResolver implements Resolver {

	@Override
    public FuzzyDataSet resolve(FuzzyDataSet dataSet) {
        Map<Map<String, FuzzySet>, String> solution =
                dataSet
                .getRecords()
                .parallelStream()
                .collect(
                        Collectors.groupingBy(FuzzyRecord::getAttributes,
                        Collectors.groupingBy(FuzzyRecord::getClazz,
                                Collectors.counting())))
                .entrySet()
                	.parallelStream()
                    .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue()
                                        .entrySet()
                                        .parallelStream()
                                        .max(Comparator.comparing(Map.Entry::getValue))
                                        .get()
                                        .getKey()));
        return new FuzzyDataSet(
                dataSet.getClazzAttribute(),
                dataSet.getClazzValues(),
                dataSet.getAttributes(),
                dataSet.getRecords()
                		.parallelStream()
                        .filter(fuzzyRecord -> solution
                                .entrySet()
                                .parallelStream()
                                .allMatch(e -> !fuzzyRecord.getAttributes().equals(e.getKey())
                                                || fuzzyRecord.getClazz().equals(e.getValue())))
                        .distinct()
                        .collect(Collectors.toList()));
    }

}
