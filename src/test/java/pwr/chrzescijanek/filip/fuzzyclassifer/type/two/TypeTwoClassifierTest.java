package pwr.chrzescijanek.filip.fuzzyclassifer.type.two;

import org.junit.Assert;
import pwr.chrzescijanek.filip.fuzzyclassifer.AbstractClassifierTest;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Record;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Fuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.AttributeReductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.ConflictResolver;
import pwr.chrzescijanek.filip.fuzzyclassifier.type.two.CustomTypeTwoDefuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.type.two.TypeTwoClassifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

public class TypeTwoClassifierTest extends AbstractClassifierTest {

    @Override
    protected void testClassifier(String clazz, List<String> clazzValues, List<String> attributes,
                                List<Record> records, List<TestRecord> testRecords, Map<String, Double> sharpValues) {
        new TypeTwoClassifier.Builder(new Fuzzifier(), new ConflictResolver(), new AttributeReductor())
                .build()
                .train(new DataSet(clazz, clazzValues, attributes, records))
                .test (new TestDataSet(attributes, testRecords));


        Map<String, Double> bottomSharpValues = new HashMap<>();
        bottomSharpValues.put("yes",   0.0);
        bottomSharpValues.put("no",  240.0);
        Map<String, Double> topSharpValues = new HashMap<>();
        topSharpValues.put("yes",   15.0);
        topSharpValues.put("no",  255.0);

        System.out.println(testRecords.get(0) + " = " + testRecords.get(0).getValue());
        System.out.println(testRecords.get(1) + " = " + testRecords.get(1).getValue());
        Assert.assertTrue(testRecords.get(0).getValue() <= 0.5);
        Assert.assertTrue(testRecords.get(1).getValue() >= 0.5);

        new TypeTwoClassifier.Builder(new Fuzzifier(), new ConflictResolver(), new AttributeReductor())
                .withDefuzzifier(new CustomTypeTwoDefuzzifier(bottomSharpValues, topSharpValues))
                .build()
                .train(new DataSet(clazz, clazzValues, attributes, records))
                .test (new TestDataSet(attributes, testRecords));

        System.out.println(testRecords.get(0) + " = " + testRecords.get(0).getValue());
        System.out.println(testRecords.get(1) + " = " + testRecords.get(1).getValue());
        Assert.assertTrue(testRecords.get(0).getValue() <= 127.5);
        Assert.assertTrue(testRecords.get(1).getValue() >= 127.5);
    }

}
