package sebastien.perpignane.diabetor.quickinsulin;

public class MealGlycemiaMeasure {

    private final double glycemiaAfterMeal;

    public MealGlycemiaMeasure(double glycemiaAfterMeal) {
        this.glycemiaAfterMeal = glycemiaAfterMeal;
    }

    public IntervalTrend getTrend(QuickInsulinAdaptationCriterion objective) {
        if (glycemiaAfterMeal < objective.getMin()) {
            return IntervalTrend.DOWN;
        }
        else if (glycemiaAfterMeal > objective.getMax()) {
            return IntervalTrend.UP;
        }
        else {
            return IntervalTrend.STABLE;
        }
    }

}
