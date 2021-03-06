package pwr.chrzescijanek.filip.fuzzyclassifier.common;

public class FuzzyLogic {

    public Double getPdfForFuzzySet(FuzzySet fuzzySet, Double mean, Double firstVariance, Double secondVariance, Double value) {
        switch(fuzzySet) {
            case LOW:    return value > mean ? 0.0 : 1.0 - getPdf(mean, secondVariance, value);
            case MEDIUM: return getPdf(mean, firstVariance, value);
            case HIGH:   return value < mean ? 0.0 : 1.0 - getPdf(mean, secondVariance, value);
            default: throw new IllegalArgumentException("Set does not exist: " + fuzzySet);
        }
    }

    public Double getPdf(Double mean, Double variance, Double value) {
        return Math.exp(-(Math.pow(value - mean, 2)) / (2 * variance));
    }

}
