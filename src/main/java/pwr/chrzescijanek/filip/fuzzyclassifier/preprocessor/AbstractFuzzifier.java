package pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzyLogic;
import pwr.chrzescijanek.filip.fuzzyclassifier.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;

public abstract class AbstractFuzzifier implements Fuzzifier {

    protected abstract FuzzyLogic getFuzzyLogic();

    @Override
    public FuzzyDataSet fuzzify(DataSet dataSet, Stats stats) {
        return new FuzzyDataSet(
                dataSet.getClazzAttribute(),
                dataSet.getClazzValues(),
                dataSet.getAttributes(),
                dataSet.getRecords()
                		.parallelStream()
                        .map(record -> new FuzzyRecord(record.getClazz(),
                        record.getAttributes()
                .entrySet()
                .parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Arrays
                                .stream(FuzzySet.values())
                                .max(Comparator.comparing(s -> getFuzzyLogic()
                                        .getPdfForFuzzySet(s,
                                                stats.getMeans().get(e.getKey()),
                                                stats.getVariances().get(e.getKey()),
                                                e.getValue()))).get())))).collect(Collectors.toList()));
    }

}
