package pwr.chrzescijanek.filip.fuzzyclassifer.type.two;

import org.junit.jupiter.api.Assumptions;
import pwr.chrzescijanek.filip.fuzzyclassifer.AbstractClassifierTest;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.DataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.raw.Record;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestDataSet;
import pwr.chrzescijanek.filip.fuzzyclassifier.data.test.TestRecord;
import pwr.chrzescijanek.filip.fuzzyclassifier.postprocessor.CustomDefuzzifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.AttributeReductor;
import pwr.chrzescijanek.filip.fuzzyclassifier.preprocessor.ConflictResolver;
import pwr.chrzescijanek.filip.fuzzyclassifier.type.two.TypeTwoClassifier;
import pwr.chrzescijanek.filip.fuzzyclassifier.type.two.TypeTwoFuzzifier;

import java.util.List;
import java.util.Map;

public class TypeTwoClassifierTest extends AbstractClassifierTest {

    @Override
    protected void testClassifier(String clazz, List<String> clazzValues, List<String> attributes,
                                List<Record> records, List<TestRecord> testRecords, Map<String, Double> sharpValues) {
        new TypeTwoClassifier.Builder(new TypeTwoFuzzifier(), new ConflictResolver(), new AttributeReductor())
                .build()
                .train(new DataSet(clazz, clazzValues, attributes, records))
                .test (new TestDataSet(attributes, testRecords));

        Assumptions.assumeTrue(testRecords.get(0).getValue() <= 0.5);
        Assumptions.assumeTrue(testRecords.get(1).getValue() >= 0.5);

        new TypeTwoClassifier.Builder(new TypeTwoFuzzifier(), new ConflictResolver(), new AttributeReductor())
                .withDefuzzifier(new CustomDefuzzifier(sharpValues))
                .build()
                .train(new DataSet(clazz, clazzValues, attributes, records))
                .test (new TestDataSet(attributes, testRecords));

        Assumptions.assumeTrue(testRecords.get(0).getValue() <= 127.5);
        Assumptions.assumeTrue(testRecords.get(1).getValue() >= 127.5);
    }

}
