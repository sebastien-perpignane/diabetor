package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class QuickInsulinAdaptationCriteriaTest {

    @Test
    void testDefaultConstructor() {
        QuickInsulinAdaptationCriteria criteria = new QuickInsulinAdaptationCriteria();

        assertThat(criteria.getMin()).isEqualTo(Double.MIN_VALUE);
        assertThat(criteria.getMax()).isEqualTo(Double.MAX_VALUE);

        assertThat(criteria.getAdaptation()).isZero();
        assertThat(criteria.isCheckAcetone()).isFalse();
        assertThat(criteria.isEndOfMeal()).isFalse();
    }

    @Test
    void testMinimalPublicConstructor() {
        QuickInsulinAdaptationCriteria criteria = new QuickInsulinAdaptationCriteria(0.7, 1.4, 1);

        assertThat(criteria.getMin()).isEqualTo(0.7);
        assertThat(criteria.getMax()).isEqualTo(1.4);

        assertThat(criteria.getAdaptation()).isEqualTo(1);
        assertThat(criteria.isCheckAcetone()).isFalse();
        assertThat(criteria.isEndOfMeal()).isFalse();
    }

    @Test
    void testFullPublicConstructor() {
        QuickInsulinAdaptationCriteria criteria = new QuickInsulinAdaptationCriteria(0.7, 1.4, 1, true, true);

        assertThat(criteria.getMin()).isEqualTo(0.7);
        assertThat(criteria.getMax()).isEqualTo(1.4);

        assertThat(criteria.getAdaptation()).isEqualTo(1);
        assertThat(criteria.isCheckAcetone()).isTrue();
        assertThat(criteria.isEndOfMeal()).isTrue();
    }

    @Test
    void testIsIncluded_true_normalInterval() {

        QuickInsulinAdaptationCriteria criteria = new QuickInsulinAdaptationCriteria(0.2, 0.7, -1);

        assertThat(criteria.isIncluded(0.3)).isTrue();

    }

    @Test
    void testIsIncluded_true_nullMin() {

        QuickInsulinAdaptationCriteria criteria = new QuickInsulinAdaptationCriteria(null, 0.7, -1);

        assertThat(criteria.isIncluded(0.3)).isTrue();

    }

    @Test
    void testIsIncluded_true_nullMax() {

        QuickInsulinAdaptationCriteria criteria = new QuickInsulinAdaptationCriteria(3.0, null, -1);

        assertThat(criteria.isIncluded(3.1)).isTrue();

    }

    @Test
    void testIsIncluded_false_nullMin() {

        QuickInsulinAdaptationCriteria criteria = new QuickInsulinAdaptationCriteria(null, 0.7, -1);

        assertThat(criteria.isIncluded(0.8)).isFalse();

    }

    @Test
    void testIsIncluded_false_nullMax() {

        QuickInsulinAdaptationCriteria criteria = new QuickInsulinAdaptationCriteria(2.0, null, -1);

        assertThat(criteria.isIncluded(0.8)).isFalse();

    }

    @Test
    void testNullMinAndNullMaxThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
            () -> new QuickInsulinAdaptationCriteria(null, null, 0)
        );
    }

}
