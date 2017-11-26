package pwr.chrzescijanek.filip.fuzzyclassifer;

import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Record;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

public abstract class AbstractClassifierTest {

    protected abstract void testClassifier(String clazz, List<String> clazzValues, List<String> attributes,
                                           List<Record> records, List<TestRecord> testRecords,
                                           Map<String, Double> sharpValues);

    @Test
    public void classify() {
        String clazz = "stain";
        List<String> clazzValues = Arrays.asList("yes", "no");
        List<String> attributes = Arrays.asList("Hue", "Saturation", "Value");
        List<Record> records = Arrays.asList(
                new Record("yes", createValues(attributes,  37.0,  93.0, 100.0)),
                new Record("yes", createValues(attributes,  33.0,  84.0,  96.0)),
                new Record("yes", createValues(attributes,  40.0,  88.0,  99.0)),
                new Record("yes", createValues(attributes,  49.0,  98.0,  98.0)),
                new Record("yes", createValues(attributes,  37.0,  95.0,  95.0)),
                new Record("no",  createValues(attributes, 261.0,  95.0,  85.0)),
                new Record("no",  createValues(attributes, 251.0,  75.0, 100.0)),
                new Record("no",  createValues(attributes, 272.0,  97.0,  85.0)),
                new Record("no",  createValues(attributes, 292.0, 100.0,  96.0)),
                new Record("no",  createValues(attributes, 326.0,  91.0,  88.0))
        );
        List<TestRecord> testRecords = Arrays.asList(
                new TestRecord(createValues(attributes,  34.0,  93.0,  100.0)),
                new TestRecord(createValues(attributes, 274.0,  87.0,   85.0))
        );
        Map<String, Double> sharpValues = new HashMap<>();
        sharpValues.put("yes",   0.0);
        sharpValues.put("no",  255.0);

        testClassifier(clazz, clazzValues, attributes, records, testRecords, sharpValues);
    }
    
    private Map<String, Double> createValues(List<String> attributes, Double... values) {
    	assert(attributes.size() == values.length);
    	Map<String, Double> result = new HashMap<>();
  
    	for(int i = 0; i < attributes.size(); i++) {
    		result.put(attributes.get(i), values[i]);
    	}
    	
    	return result;
    }

}
