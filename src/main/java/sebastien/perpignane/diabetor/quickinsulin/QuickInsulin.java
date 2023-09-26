package sebastien.perpignane.diabetor.quickinsulin;

import sebastien.perpignane.diabetor.acetone.Acetone;
import sebastien.perpignane.diabetor.acetone.AcetoneCriterionRepositoryJSonFileImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuickInsulin {

    private final QuickInsulinAdaptationCriteriaRepository adaptationCriteriaRepository;

    private final QuickInsulinAdaptationRepository adaptationRepository;

    public QuickInsulin(QuickInsulinAdaptationCriteriaRepository adaptationCriteriaRepository, QuickInsulinAdaptationRepository adaptationRepository) {
        this.adaptationCriteriaRepository = adaptationCriteriaRepository;
        this.adaptationRepository = adaptationRepository;
    }

    public PunctualQuickInsulinAdaptationResult computePunctualAdaptation(double glycemia) throws AcetoneLevelRequiredException, IOException {
        if (glycemia > 2.5) {
            throw new AcetoneLevelRequiredException("Please provide acetone level");
        }
        return computePunctualAdaptation(glycemia, 0);
    }

    public PunctualQuickInsulinAdaptationResult computePunctualAdaptation(double glycemia, int acetoneLevel) throws IOException {

        // FIXME IoC
        Acetone acetone = new Acetone(new AcetoneCriterionRepositoryJSonFileImpl());
        int acetoneAdaptation = acetone.computeAdaptation(acetoneLevel);

         QuickInsulinAdaptationCriterion quickInsulinAdaptationCriterion =
                adaptationCriteriaRepository.findAll().stream()
                        .filter(c -> c.isIncluded(glycemia))
                        .findFirst()
                        .orElseThrow();

         int glycemiaAdaptation = quickInsulinAdaptationCriterion.getAdaptation();

        return new PunctualQuickInsulinAdaptationResult(
            glycemiaAdaptation,
            acetoneAdaptation,
            quickInsulinAdaptationCriterion.isEndOfMeal()
        );
    }

    public int computeLongTermAdaptation(List<MealGlycemiaInterval> mealIntervals) {

        checkMealIntervalsValidity(mealIntervals);

        Map<IntervalTrend, Integer> adaptationByTrend =
                adaptationRepository.findAll().stream()
                        .collect(
                                Collectors.toMap(
                                        qia -> IntervalTrend.valueOf(qia.getTrend()),
                                        QuickInsulinAdaptation::getAdaptation
                                )
                        );

        QuickInsulinAdaptationCriterion objectiveCriterion = getObjectiveCriterion();

        var trends = mealIntervals.stream()
                .map( mgi -> mgi.getTrend(objectiveCriterion) )
                .collect(Collectors.toSet());

        if (trends.size() == 1) {
            var uniqueTrend = trends.iterator().next();
            return adaptationByTrend.get(uniqueTrend);
        }

        return adaptationByTrend.get(IntervalTrend.STABLE);
    }

    void checkMealIntervalsValidity(List<MealGlycemiaInterval> mealIntervals) throws IllegalArgumentException {
        if (mealIntervals == null) {
            throw new IllegalArgumentException("mealIntervals cannot be null");
        }
        if (mealIntervals.size() < 3) {
            throw new IllegalArgumentException("mealIntervals must contain at least 3 intervals");
        }
    }

    private QuickInsulinAdaptationCriterion getObjectiveCriterion() {
        return adaptationCriteriaRepository.findAll().stream().filter(QuickInsulinAdaptationCriterion::isObjective).findFirst().orElseThrow();
    }

}
