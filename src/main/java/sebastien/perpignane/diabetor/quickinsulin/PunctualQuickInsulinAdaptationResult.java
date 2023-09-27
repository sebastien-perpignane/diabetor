package sebastien.perpignane.diabetor.quickinsulin;

public record PunctualQuickInsulinAdaptationResult(int glycemiaAdaptation, int acetoneAdaptation, boolean endOfMeal) {

    public int getAdaptation() {
        return glycemiaAdaptation + acetoneAdaptation;
    }

}
