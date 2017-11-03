package pwr.chrzescijanek.filip.fuzzyclassifier.model.preprocessor;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.fuzzy.FuzzyRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Stats;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.common.FuzzySet;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.common.NormalDistribution;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Fuzzifier {

    public FuzzyDataSet fuzzify(DataSet dataSet, Stats stats) {
        return new FuzzyDataSet(
                dataSet.getClazzAttribute(),
                dataSet.getClazzValues(),
                dataSet.getAttributes(),
                dataSet.getRecords()
                        .stream().map(record -> new FuzzyRecord(record.getClazz(),
                        record.getAttributes()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Arrays
                                .stream(FuzzySet.values())
                                .max(Comparator.comparing(s -> new NormalDistribution()
                                        .getPdfForFuzzySet(s,
                                                stats.getMeans().get(e.getKey()),
                                                stats.getVariances().get(e.getKey()),
                                                e.getValue()))).get())))).collect(Collectors.toList()));
    }

}