package sebastien.perpignane.diabetor.quickinsulin;

public class PunctualQuickInsulinAdaptationResult {

    private final int glycemiaAdaptation;

    private final int acetoneAdaptation;

    private final boolean endOfMeal;

    public PunctualQuickInsulinAdaptationResult(int glycemiaAdaptation, int acetoneAdaptation, boolean endOfMeal) {
        this.glycemiaAdaptation = glycemiaAdaptation;
        this.acetoneAdaptation = acetoneAdaptation;
        this.endOfMeal = endOfMeal;
    }

    public int getGlycemiaAdaptation() {
        return glycemiaAdaptation;
    }

    public int getAcetoneAdaptation() {
        return acetoneAdaptation;
    }

    public int getAdaptation() {
        return glycemiaAdaptation + acetoneAdaptation;
    }

    public boolean isEndOfMeal() {
        return endOfMeal;
    }

}
