package pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;

public abstract class AbstractFuzzifier {

    public FuzzyDataSet fuzzify(DataSet dataSet, Stats stats) {
        return new FuzzyDataSet(
                dataSet.getClazzAttribute(),
                dataSet.getClazzValues(),
                dataSet.getAttributes(),
                dataSet.getRecords().parallelStream()
                	.map(record -> new FuzzyRecord(record.getClazz(),
                        record.getAttributes()
                                .entrySet()
                                .parallelStream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> Arrays
                                                .stream(FuzzySet.values())
                                                .max(Comparator.comparing(s -> getProbability(stats, e, s))).get())))).collect(Collectors.toList()));
    }

	protected abstract Double getProbability(Stats stats, Entry<String, Double> e, FuzzySet s);

}
