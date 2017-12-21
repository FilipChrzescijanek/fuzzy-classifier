package pwr.chrzescijanek.filip.fuzzyclassifer.type.one;

import org.junit.Assert;
import pwr.chrzescijanek.filip.fuzzyclassifer.AbstractClassifierTest;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Record;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.Fuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.type.one.CustomTypeOneDefuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.AttributeReductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.ConflictResolver;
import pwr.chrzescijanek.filip.fuzzyclassifier.type.one.TypeOneClassifier;

import java.util.List;
import java.util.Map;

public class TypeOneClassifierTest extends AbstractClassifierTest {

    @Override
    protected void testClassifier(String clazz, List<String> clazzValues, List<String> attributes,
                                List<Record> records, List<TestRecord> testRecords, Map<String, Double> sharpValues) {
        new TypeOneClassifier.Builder(new Fuzzifier(), new ConflictResolver(), new AttributeReductor())
                .build()
                .train(new DataSet(clazz, clazzValues, attributes, records))
                .test (new TestDataSet(attributes, testRecords));

        System.out.println(testRecords.get(0).getValue());
        System.out.println(testRecords.get(1).getValue());
        Assert.assertTrue(testRecords.get(0).getValue() <= 0.5);
        Assert.assertTrue(testRecords.get(1).getValue() >= 0.5);

        new TypeOneClassifier.Builder(new Fuzzifier(), new ConflictResolver(), new AttributeReductor())
                .withDefuzzifier(new CustomTypeOneDefuzzifier(sharpValues))
                .build()
                .train(new DataSet(clazz, clazzValues, attributes, records))
                .test (new TestDataSet(attributes, testRecords));

        System.out.println(testRecords.get(0).getValue());
        System.out.println(testRecords.get(1).getValue());
        Assert.assertTrue(testRecords.get(0).getValue() <= 127.5);
        Assert.assertTrue(testRecords.get(1).getValue() >= 127.5);
    }

}
