package sebastien.perpignane.diabetor.quickinsulin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuickInsulinTest {

    private static final List<QuickInsulinAdaptationCriterion> criteria =
            List.of(
                new QuickInsulinAdaptationCriterion(null, 0.7, -1, false, true, false),
                new QuickInsulinAdaptationCriterion(0.7, 1.4, 0, true, false, false),
                new QuickInsulinAdaptationCriterion(1.4, 2.0, 1),
                new QuickInsulinAdaptationCriterion(2.0, 2.5, 2),
                new QuickInsulinAdaptationCriterion(2.5, 3.0, 3),
                new QuickInsulinAdaptationCriterion(3.0, null, 4)
            );

    private static final List<QuickInsulinAdaptation> adaptations =
            List.of(
                new QuickInsulinAdaptation("UP", +2),
                new QuickInsulinAdaptation("DOWN", -2),
                new QuickInsulinAdaptation("STABLE", 0)
            );
    private QuickInsulin quickInsulin;

    @BeforeEach
    void setUp() {
        QuickInsulinAdaptationCriteriaRepository adaptationCriteriaRepository = mock(QuickInsulinAdaptationCriteriaRepository.class);
        when(adaptationCriteriaRepository.findAll()).thenReturn(criteria);

        QuickInsulinAdaptationRepository adaptationRepository = mock(QuickInsulinAdaptationRepository.class);
        when(adaptationRepository.findAll()).thenReturn(adaptations);

        quickInsulin = new QuickInsulin(adaptationCriteriaRepository, adaptationRepository);

    }

    @Test
    void testComputeAdaptation_inObjective() throws AcetoneLevelRequiredException, IOException {

        double glycemia = 0.8;

        PunctualQuickInsulinAdaptationResult adaptationResult = quickInsulin.computePunctualAdaptation(glycemia);

        assertThat(adaptationResult.getAdaptation()).isZero();

    }

    @Test
    void testComputeAdaptation_outOfObjective_acetoneLevelNotRequired() throws AcetoneLevelRequiredException, IOException {

        double glycemia = 1.6;

        PunctualQuickInsulinAdaptationResult adaptationResult = quickInsulin.computePunctualAdaptation(glycemia);

        assertThat(adaptationResult.getAdaptation()).isEqualTo(1);

    }

    @Test
    void testComputeAdaptation_outOfObjective_hypoglycemia() throws AcetoneLevelRequiredException, IOException {

        double glycemia = 0.65;

        PunctualQuickInsulinAdaptationResult adaptationResult = quickInsulin.computePunctualAdaptation(glycemia);

        assertThat(adaptationResult.getAdaptation())
            .isEqualTo(-1);
        assertThat(adaptationResult.endOfMeal())
            .isTrue();

    }

    @Test
    void testComputeAdaptation_acetoneLevelRequired() {

        double glycemia = 2.51;

        assertThatExceptionOfType(QuickInsulinException.class).isThrownBy(
            () -> quickInsulin.computePunctualAdaptation(glycemia)
        );

    }

    @Test
    void testComputeLongTermAdaptation_noAdaptation_goodTrend() {

        List<MealGlycemiaMeasure> intervals = List.of(
            new MealGlycemiaMeasure(1.2),
            new MealGlycemiaMeasure(1.4),
            new MealGlycemiaMeasure(0.8)
        );

        int longTermAdaptation = quickInsulin.computeLongTermAdaptation(intervals);

        assertThat(longTermAdaptation).isZero();

    }

    @Test
    void testComputeLongTermAdaptation_noAdaptation_noTrend() {

        List<MealGlycemiaMeasure> intervals = List.of(
                new MealGlycemiaMeasure(1.6),
                new MealGlycemiaMeasure(0.6),
                new MealGlycemiaMeasure(0.8)
        );

        int longTermAdaptation = quickInsulin.computeLongTermAdaptation(intervals);

        assertThat(longTermAdaptation).isZero();

    }

    @Test
    void testComputeLongTermAdaptation_adaptationUp() {

        List<MealGlycemiaMeasure> intervals = List.of(
                new MealGlycemiaMeasure(1.5),
                new MealGlycemiaMeasure(1.8),
                new MealGlycemiaMeasure(1.42)
        );

        int longTermAdaptation = quickInsulin.computeLongTermAdaptation(intervals);

        assertThat(longTermAdaptation).isEqualTo(+2);

    }

    @Test
    void testComputeLongTermAdaptation_adaptationDown() {

        List<MealGlycemiaMeasure> intervals = List.of(
                new MealGlycemiaMeasure(0.65),
                new MealGlycemiaMeasure(0.60),
                new MealGlycemiaMeasure(0.59)
        );

        int longTermAdaptation = quickInsulin.computeLongTermAdaptation(intervals);

        assertThat(longTermAdaptation).isEqualTo(-2);

    }

    @Test
    void testComputeLongTermAdaptation_invalidArgument_nullIntervals() {

        assertThatIllegalArgumentException().isThrownBy(
            () -> quickInsulin.computeLongTermAdaptation(null)
        );

    }

    @Test
    void testComputeLongTermAdaptation_invalidArgument_intervalsSize() {

        List<MealGlycemiaMeasure> intervals =
                List.of(
                    new MealGlycemiaMeasure(0.65),
                    new MealGlycemiaMeasure(0.60)
                );

        assertThatIllegalArgumentException().isThrownBy(
            () -> quickInsulin.computeLongTermAdaptation(intervals)
        );

    }

}
