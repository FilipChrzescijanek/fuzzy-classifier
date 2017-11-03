package pwr.chrzescijanek.filip.fuzzyclassifier.model.common;

public class NormalDistribution {

    public Double getPdfForFuzzySet(FuzzySet fuzzySet, Double mean, Double variance, Double value) {
        switch(fuzzySet) {
            case LOW:    return value > mean ? 0.0 : 1 - getPdf(mean, variance, value);
            case MEDIUM: return getPdf(mean, variance, value);
            case HIGH:   return value < mean ? 0.0 : 1 - getPdf(mean, variance, value);
            default: throw new IllegalArgumentException("Set does not exist: " + fuzzySet);
        }
    }

    public Double getPdf(Double mean, Double variance, Double value) {
        return Math.exp(-(Math.pow(value - mean, 2)) / (2 * variance)) / Math.sqrt(2 * Math.PI * variance);
    }

}
