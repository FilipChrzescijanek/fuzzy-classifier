package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasicTypeTwoDefuzzifier extends AbstractTypeTwoDefuzzifier {

    public BasicTypeTwoDefuzzifier(List<String> clazzValues) {
        super(clazzValues.parallelStream()
                        .collect(Collectors.toMap(
                                Function.identity(), v -> clazzValues.indexOf(v) - 0.1
                        )),
                clazzValues.parallelStream()
                        .collect(Collectors.toMap(
                                Function.identity(), v -> clazzValues.indexOf(v) + 0.1
                        )));
    }

}
