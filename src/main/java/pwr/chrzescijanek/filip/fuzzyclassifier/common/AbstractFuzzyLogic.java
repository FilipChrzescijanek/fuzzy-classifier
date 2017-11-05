package pwr.chrzescijanek.filip.fuzzyclassifier.common;

public abstract class AbstractFuzzyLogic implements FuzzyLogic {

    @Override
    public Double getPdfForFuzzySet(FuzzySet fuzzySet, Double mean, Double variance, Double value) {
        switch(fuzzySet) {
            case LOW:    return value > mean ? 0.0 : 1.0 - getPdf(mean, variance, value);
            case MEDIUM: return getPdf(mean, variance, value);
            case HIGH:   return value < mean ? 0.0 : 1.0 - getPdf(mean, variance, value);
            default: throw new IllegalArgumentException("Set does not exist: " + fuzzySet);
        }
    }

    @Override
    public Double getPdf(Double mean, Double variance, Double value) {
        return Math.exp(-(Math.pow(value - mean, 2)) / (2 * variance));
    }

}
