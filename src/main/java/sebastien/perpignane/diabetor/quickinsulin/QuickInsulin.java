package sebastien.perpignane.diabetor.quickinsulin;

import java.util.List;
import java.util.stream.Collectors;

public class QuickInsulin {

    private final QuickInsulinAdaptationCriteriaRepository repository;

    public QuickInsulin(QuickInsulinAdaptationCriteriaRepository repository) {
        this.repository = repository;
    }

    public QuickInsulinAdaptationCriterion computePunctualAdaptation(double glycemia) {
        return repository.findAll().stream().filter(c -> c.isIncluded(glycemia)).findFirst().orElseThrow();
    }

    public int computeLongTermAdaptation(List<MealGlycemiaInterval> mealIntervals) {
        if (mealIntervals == null) {
            throw new IllegalArgumentException("mealIntervals cannot be null");
        }
        if (mealIntervals.size() < 3) {
            throw new IllegalArgumentException("mealIntervals must contain at least 3 intervals");
        }

        var trends = mealIntervals.stream().map(MealGlycemiaInterval::getTrend).collect(Collectors.toSet());

        if (trends.size() == 1) {
            return trends.iterator().next().getAdaptation();
        }

        return IntervalTrend.STABLE.getAdaptation();
    }

}
