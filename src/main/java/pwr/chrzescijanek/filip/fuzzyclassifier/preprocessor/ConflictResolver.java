package pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class ConflictResolver implements Resolver {

    @Override
    public FuzzyDataSet resolve(FuzzyDataSet dataSet) {
        Map<Map<String, FuzzySet>, String> solution =
                dataSet
                .getRecords()
                .stream()
                .collect(
                        Collectors.groupingBy(FuzzyRecord::getAttributes,
                        Collectors.groupingBy(FuzzyRecord::getClazz,
                                Collectors.counting())))
                .entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue()
                                        .entrySet()
                                        .stream()
                                        .max(Comparator.comparing(Map.Entry::getValue))
                                        .get()
                                        .getKey()));
        return new FuzzyDataSet(
                dataSet.getClazzAttribute(),
                dataSet.getClazzValues(),
                dataSet.getAttributes(),
                dataSet.getRecords()
                        .stream()
                        .filter(fuzzyRecord -> solution
                                .entrySet()
                                .stream()
                                .allMatch(e -> !fuzzyRecord.getAttributes().equals(e.getKey())
                                                || fuzzyRecord.getClazz().equals(e.getValue())))
                        .distinct()
                        .collect(Collectors.toList()));
    }

}
