package pwr.chrzescijanek.filip.fuzzyclassifer;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import pwr.chrzescijanek.filip.fuzzyclassifier.*;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Record;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.model.postprocessor.Defuzzifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClassifierTest {

    @Test
    public void classify() {
        String clazz = "stain";
        List<String> clazzValues = Arrays.asList("no", "yes");
        List<String> attributes  = Arrays.asList("Hue", "Saturation", "Value");
        List<Record> records     = Arrays.asList(
          new Record("yes", attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 2.0))),
          new Record("yes", attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 1.5))),
          new Record("no",  attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 1.0))),
          new Record("no",  attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 0.5)))
        );
        List<TestRecord> testRecords     = Arrays.asList(
                new TestRecord(attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 2.50))),
                new TestRecord(attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 0.00)))
        );
        Map<String, Double> sharpValues = new HashMap<>();
        sharpValues.put("yes", 255.0);
        sharpValues.put("no", 0.0);

        new Classifier()
                .train(new DataSet(clazz, clazzValues, attributes, records))
                .test (new TestDataSet(attributes, testRecords));

        Assumptions.assumeTrue(testRecords.get(0).getValue() >= 0.5);
        Assumptions.assumeTrue(testRecords.get(1).getValue() <= 0.5);

        new Classifier()
                .train(new DataSet(clazz, clazzValues, attributes, records))
                .test (new TestDataSet(attributes, testRecords), new Defuzzifier(sharpValues));

        Assumptions.assumeTrue(testRecords.get(0).getValue() >= 127.5);
        Assumptions.assumeTrue(testRecords.get(1).getValue() <= 127.5);
    }

}
