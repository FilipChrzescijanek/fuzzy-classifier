package pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyRecord;

public class AttributeReductor implements Reductor {

	@Override
    public FuzzyDataSet reduce(FuzzyDataSet dataSet){
        List<FuzzyRecord> records      = dataSet.getRecords();
        List<List<String>> differences = getDifferences(dataSet, records);
        List<String> newAttributes     = getNewAttributes(differences);

        return new FuzzyDataSet(
                dataSet.getClazzAttribute(),
                dataSet.getClazzValues(),
                newAttributes,
                records
                		.parallelStream()
                        .map(record -> new FuzzyRecord(
                                record.getClazz(),
                                record.getAttributes()
                                        .entrySet()
                                        .parallelStream()
                                        .filter(e -> newAttributes.contains(e.getKey()))
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))
                        .collect(Collectors.toList()));
    }

    private List<List<String>> getDifferences(FuzzyDataSet dataSet, List<FuzzyRecord> records) {
        List<List<String>> differences = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            for (int j = i + 1; j < records.size(); j++) {
                final FuzzyRecord first = records.get(i);
                final FuzzyRecord second = records.get(j);
                List<String> difference = dataSet.getAttributes()
                        .parallelStream()
                        .filter(attribute ->
                                !first.getAttributes().get(attribute)
                                        .equals(second.getAttributes().get(attribute)))
                        .collect(Collectors.toList());
                if (!difference.isEmpty()) {
                    differences.add(difference);
                }
            }
        }

        return differences;
    }

    private List<String> getNewAttributes(List<List<String>> differences) {
        List<String> newAttributes = new ArrayList<>();
        while (!differences.isEmpty()) {
            final String attribute = differences
                    .parallelStream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .parallelStream()
                    .max(Comparator.comparing(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .get();
            newAttributes.add(attribute);
            differences = differences
                    .parallelStream()
                    .filter(difference -> !difference.contains(attribute))
                    .collect(Collectors.toList());
        }
        return newAttributes;
    }

}
