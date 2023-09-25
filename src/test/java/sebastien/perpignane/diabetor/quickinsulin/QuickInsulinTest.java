package sebastien.perpignane.diabetor.quickinsulin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuickInsulinTest {

    private static final List<QuickInsulinAdaptationCriterion> criteria =
            List.of(
                new QuickInsulinAdaptationCriterion(null, 0.7, -1),
                new QuickInsulinAdaptationCriterion(0.7, 1.4, 0),
                new QuickInsulinAdaptationCriterion(1.4, 2.0, 1),
                new QuickInsulinAdaptationCriterion(2.0, 2.5, 2),
                new QuickInsulinAdaptationCriterion(2.5, 3.0, 3),
                new QuickInsulinAdaptationCriterion(3.0, null, 4)
            );

    @Test
    void testComputeAdaptation() {

        QuickInsulinAdaptationCriteriaRepository repository = mock(QuickInsulinAdaptationCriteriaRepository.class);

        double glycemia = 0.8;

        when(repository.findAll()).thenReturn(criteria);

        QuickInsulin quickInsulin = new QuickInsulin(repository);

        var result = quickInsulin.computePunctualAdaptation(glycemia);

        assertThat(result).isNotNull();

        assertThat(result.getAdaptation()).isZero();

    }

    @Test
    void testComputeLongTermAdaptation_noAdaptation_goodTrend() {

        var objective = new QuickInsulinAdaptationCriterion(0.7, 1.4, 0);

        List<MealGlycemiaInterval> intervals = List.of(
            new MealGlycemiaInterval(1.2,  objective),
            new MealGlycemiaInterval(1.4,  objective),
            new MealGlycemiaInterval(0.8,  objective)
        );

        QuickInsulinAdaptationCriteriaRepository repository = mock(QuickInsulinAdaptationCriteriaRepository.class);

        QuickInsulin quickInsulin = new QuickInsulin(repository);

        int longTermAdaptation = quickInsulin.computeLongTermAdaptation(intervals);

        assertThat(longTermAdaptation).isZero();

    }

    @Test
    void testComputeLongTermAdaptation_noAdaptation_noTrend() {

        var objective = new QuickInsulinAdaptationCriterion(0.7, 1.4, 0);

        List<MealGlycemiaInterval> intervals = List.of(
                new MealGlycemiaInterval(1.6,  objective),
                new MealGlycemiaInterval(0.6,  objective),
                new MealGlycemiaInterval(0.8,  objective)
        );

        QuickInsulinAdaptationCriteriaRepository repository = mock(QuickInsulinAdaptationCriteriaRepository.class);

        QuickInsulin quickInsulin = new QuickInsulin(repository);

        int longTermAdaptation = quickInsulin.computeLongTermAdaptation(intervals);

        assertThat(longTermAdaptation).isEqualTo(IntervalTrend.STABLE.getAdaptation());

    }

    @Test
    void testComputeLongTermAdaptation_adaptationUp() {

        var objective = new QuickInsulinAdaptationCriterion(0.7, 1.4, 0);

        List<MealGlycemiaInterval> intervals = List.of(
                new MealGlycemiaInterval(1.5,  objective),
                new MealGlycemiaInterval(1.8,  objective),
                new MealGlycemiaInterval(1.42,  objective)
        );

        QuickInsulinAdaptationCriteriaRepository repository = mock(QuickInsulinAdaptationCriteriaRepository.class);

        QuickInsulin quickInsulin = new QuickInsulin(repository);

        int longTermAdaptation = quickInsulin.computeLongTermAdaptation(intervals);

        assertThat(longTermAdaptation).isEqualTo(IntervalTrend.UP.getAdaptation());

    }

    @Test
    void testComputeLongTermAdaptation_adaptationDown() {

        var objective = new QuickInsulinAdaptationCriterion(0.7, 1.4, 0);

        List<MealGlycemiaInterval> intervals = List.of(
                new MealGlycemiaInterval(0.65,  objective),
                new MealGlycemiaInterval(0.60,  objective),
                new MealGlycemiaInterval(0.59,  objective)
        );

        QuickInsulinAdaptationCriteriaRepository repository = mock(QuickInsulinAdaptationCriteriaRepository.class);

        QuickInsulin quickInsulin = new QuickInsulin(repository);

        int longTermAdaptation = quickInsulin.computeLongTermAdaptation(intervals);

        assertThat(longTermAdaptation).isEqualTo(IntervalTrend.DOWN.getAdaptation());

    }

    @Test
    void testComputeLongTermAdaptation_invalidArgument_nullIntervals() {

        QuickInsulinAdaptationCriteriaRepository repository = mock(QuickInsulinAdaptationCriteriaRepository.class);

        QuickInsulin quickInsulin = new QuickInsulin(repository);

        assertThatIllegalArgumentException().isThrownBy(
            () -> quickInsulin.computeLongTermAdaptation(null)
        );

    }

    @Test
    void testComputeLongTermAdaptation_invalidArgument_intervalsSize() {

        var objective = new QuickInsulinAdaptationCriterion(0.7, 1.4, 0);

        List<MealGlycemiaInterval> intervals =
                List.of(
                    new MealGlycemiaInterval(0.65,  objective),
                    new MealGlycemiaInterval(0.60,  objective)
                );

        QuickInsulinAdaptationCriteriaRepository repository = mock(QuickInsulinAdaptationCriteriaRepository.class);

        QuickInsulin quickInsulin = new QuickInsulin(repository);

        assertThatIllegalArgumentException().isThrownBy(
            () -> quickInsulin.computeLongTermAdaptation(intervals)
        );

    }


}