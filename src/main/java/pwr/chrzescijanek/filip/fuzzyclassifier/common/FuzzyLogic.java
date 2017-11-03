package pwr.chrzescijanek.filip.fuzzyclassifier.common;

public interface FuzzyLogic {

    Double getPdfForFuzzySet(FuzzySet fuzzySet, Double mean, Double variance, Double value);
    Double getPdf           (Double mean, Double variance, Double value);

}
