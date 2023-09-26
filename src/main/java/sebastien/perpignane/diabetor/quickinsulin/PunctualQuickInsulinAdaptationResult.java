package sebastien.perpignane.diabetor.quickinsulin;

public class PunctualQuickInsulinAdaptationResult {

    private final int adaptation;

    private final boolean endOfMeal;

    public PunctualQuickInsulinAdaptationResult(int adaptation, boolean endOfMeal) {
        this.adaptation = adaptation;
        this.endOfMeal = endOfMeal;
    }

    public int getAdaptation() {
        return adaptation;
    }

    public boolean isEndOfMeal() {
        return endOfMeal;
    }

}
