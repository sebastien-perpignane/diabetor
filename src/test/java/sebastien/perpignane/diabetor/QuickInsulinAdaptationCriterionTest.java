package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class QuickInsulinAdaptationCriterionTest {

    @Test
    void testDefaultConstructor() {
        QuickInsulinAdaptationCriterion criteria = new QuickInsulinAdaptationCriterion();

        assertThat(criteria.getMin()).isEqualTo(Double.MIN_VALUE);
        assertThat(criteria.getMax()).isEqualTo(Double.MAX_VALUE);

        assertThat(criteria.getAdaptation()).isZero();
        assertThat(criteria.isCheckAcetone()).isFalse();
        assertThat(criteria.isEndOfMeal()).isFalse();
    }

    @Test
    void testMinimalPublicConstructor() {

        double min = 0.7;
        double max = 1.4;
        int adaptation = 1;

        QuickInsulinAdaptationCriterion criteria = new QuickInsulinAdaptationCriterion(min, max, adaptation);

        assertThat(criteria.getMin()).isEqualTo(min);
        assertThat(criteria.getMax()).isEqualTo(max);

        assertThat(criteria.getAdaptation()).isEqualTo(adaptation);
        assertThat(criteria.isCheckAcetone()).isFalse();
        assertThat(criteria.isEndOfMeal()).isFalse();
    }

    @Test
    void testFullPublicConstructor() {
        QuickInsulinAdaptationCriterion criteria = new QuickInsulinAdaptationCriterion(0.7, 1.4, 1, true, true);

        assertThat(criteria.getMin()).isEqualTo(0.7);
        assertThat(criteria.getMax()).isEqualTo(1.4);

        assertThat(criteria.getAdaptation()).isEqualTo(1);
        assertThat(criteria.isCheckAcetone()).isTrue();
        assertThat(criteria.isEndOfMeal()).isTrue();
    }

    @Test
    void testIsIncluded_true_normalInterval() {

        QuickInsulinAdaptationCriterion criteria = new QuickInsulinAdaptationCriterion(0.2, 0.7, -1);

        assertThat(criteria.isIncluded(0.3)).isTrue();

    }

    @Test
    void testIsIncluded_true_nullMin() {

        QuickInsulinAdaptationCriterion criteria = new QuickInsulinAdaptationCriterion(null, 0.7, -1);

        assertThat(criteria.isIncluded(0.3)).isTrue();

    }

    @Test
    void testIsIncluded_true_nullMax() {

        QuickInsulinAdaptationCriterion criteria = new QuickInsulinAdaptationCriterion(3.0, null, -1);

        assertThat(criteria.isIncluded(3.1)).isTrue();

    }

    @Test
    void testIsIncluded_false_nullMin() {

        QuickInsulinAdaptationCriterion criteria = new QuickInsulinAdaptationCriterion(null, 0.7, -1);

        assertThat(criteria.isIncluded(0.8)).isFalse();

    }

    @Test
    void testIsIncluded_false_nullMax() {

        QuickInsulinAdaptationCriterion criteria = new QuickInsulinAdaptationCriterion(2.0, null, -1);

        assertThat(criteria.isIncluded(0.8)).isFalse();

    }

    @Test
    void testNullMinAndNullMaxThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
            () -> new QuickInsulinAdaptationCriterion(null, null, 0)
        );
    }

}
