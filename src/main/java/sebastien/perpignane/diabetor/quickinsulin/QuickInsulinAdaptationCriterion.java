package sebastien.perpignane.diabetor.quickinsulin;

public class QuickInsulinAdaptationCriterion {

    private Double min = null;

    private Double max = null;

    private int adaptation;

    private boolean objective;

    private boolean endOfMeal = false;

    private boolean checkAcetone = false;

    /**
     * exists only for Jackson
     */
    QuickInsulinAdaptationCriterion() {
    }

    public QuickInsulinAdaptationCriterion(Double min, Double max, int adaptation) {
        this(min, max, adaptation, false, false, false);
    }

    public QuickInsulinAdaptationCriterion(Double min, Double max, int adaptation, boolean objective, boolean endOfMeal, boolean checkAcetone) {

        if (min== null && max == null) {
            throw new IllegalArgumentException("min or max must be defined");
        }
        this.min = min;
        this.max = max;
        this.adaptation = adaptation;
        this.objective = objective;
        this.endOfMeal = endOfMeal;
        this.checkAcetone = checkAcetone;
    }

    public Double getMin() {
        if (min == null) {
            return Double.MIN_VALUE;
        }
        return min;
    }

    public Double getMax() {
        if (max == null) {
            return Double.MAX_VALUE;
        }
        return max;
    }

    public int getAdaptation() {
        return adaptation;
    }

    public boolean isEndOfMeal() {
        return endOfMeal;
    }

    public boolean isCheckAcetone() {
        return checkAcetone;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setMin(Double min) {
        this.min = min;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setMax(Double max) {
        this.max = max;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setAdaptation(int adaptation) {
        this.adaptation = adaptation;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setObjective(boolean objective) {
        this.objective = objective;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setEndOfMeal(boolean endOfMeal) {
        this.endOfMeal = endOfMeal;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setCheckAcetone(boolean checkAcetone) {
        this.checkAcetone = checkAcetone;
    }

    public boolean isIncluded(Double glycemia) {
        return glycemia >= getMin() && glycemia < getMax();
    }

    public boolean isObjective() {
        return objective;
    }

}
