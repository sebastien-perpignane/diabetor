package sebastien.perpignane.diabetor.quickinsulin;

public class MealGlycemiaInterval {

    private final IntervalTrend trend;

    public MealGlycemiaInterval(Double glycemiaAfterMeal, QuickInsulinAdaptationCriterion objective) {

        if (glycemiaAfterMeal < objective.getMin()) {
            trend = IntervalTrend.DOWN;
        }
        else if (glycemiaAfterMeal > objective.getMax()) {
            trend = IntervalTrend.UP;
        }
        else {
            trend = IntervalTrend.STABLE;
        }

    }

    public IntervalTrend getTrend() {
        return trend;
    }

}
