package pwr.chrzescijanek.filip.fuzzyclassifer;

import org.junit.jupiter.api.Test;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Record;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractClassifierTest {

    protected abstract void testClassifier(String clazz, List<String> clazzValues, List<String> attributes,
                                           List<Record> records, List<TestRecord> testRecords,
                                           Map<String, Double> sharpValues);

    @Test
    public void classify() {
        String clazz = "stain";
        List<String> clazzValues = Arrays.asList("no", "yes");
        List<String> attributes = Arrays.asList("Hue", "Saturation", "Value");
        List<Record> records = Arrays.asList(
                new Record("yes", attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 2.0))),
                new Record("yes", attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 1.5))),
                new Record("no", attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 1.0))),
                new Record("no", attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 0.5)))
        );
        List<TestRecord> testRecords = Arrays.asList(
                new TestRecord(attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 2.50))),
                new TestRecord(attributes.stream().collect(Collectors.toMap(Function.identity(), attr -> 0.00)))
        );
        Map<String, Double> sharpValues = new HashMap<>();
        sharpValues.put("yes", 255.0);
        sharpValues.put("no", 0.0);

        testClassifier(clazz, clazzValues, attributes, records, testRecords, sharpValues);
    }

}
