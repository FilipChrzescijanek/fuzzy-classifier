package pwr.chrzescijanek.filip.fuzzyclassifier.type.two;

public class Range {

    private final Double left;
    private final Double right;

    public Range(Double left, Double right) {
        this.left = left;
        this.right = right;
    }

    public Double getLeft() {
        return left;
    }

    public Double getRight() {
        return right;
    }

    @Override
    public String toString() {
    	return String.format("<%.3f, %.3f>", left, right);
    }
    
}
