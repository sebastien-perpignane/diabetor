package sebastien.perpignane.diabetor.quickinsulin;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class QuickInsulinCriteriaValidatorTest {

    @Test
    void testValidate_validMinimalCriteria() {

        List<QuickInsulinAdaptationCriterion> criteria =
            List.of(
                new QuickInsulinAdaptationCriterion(null, 0.8, 0),
                    new QuickInsulinAdaptationCriterion(0.8, null, 0, true, false, false)
            );

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();

        assertDoesNotThrow(
            () -> validator.validate(criteria)
        );

    }

    @Test
    void testValidate_validCriteria() {

        List<QuickInsulinAdaptationCriterion> criteria =
                List.of(
                    new QuickInsulinAdaptationCriterion(null, 0.8, 0),
                    new QuickInsulinAdaptationCriterion(0.8, 1.9, 0, true, false, false),
                    new QuickInsulinAdaptationCriterion(1.9, 2.6, 0),
                    new QuickInsulinAdaptationCriterion(2.6, null, 0)
                );

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();

        assertDoesNotThrow(
            () -> validator.validate(criteria)
        );

    }

    @Test
    void testValidate_illegalArgument_nullCriteria() {

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(
                    () -> validator.validate(null)
                );
    }

    @Test
    void testValidate_illegalArgument_lessThan2Criteria() {

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();
        List<QuickInsulinAdaptationCriterion> emptyCriteria = new ArrayList<>();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(
                    () -> validator.validate(emptyCriteria)
                );

    }

    @Test
    void testValidate_invalid_missingObjective() {

        List<QuickInsulinAdaptationCriterion> criteria =
                List.of(
                    new QuickInsulinAdaptationCriterion(null, 0.8, 0),
                    new QuickInsulinAdaptationCriterion(0.8, null, 0)
                );

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(
                    () -> validator.validate(criteria)
                ).withMessageContaining("defined as objective");

    }

    @Test
    void testValidate_invalid_notConsecutiveMaxMin() {

        List<QuickInsulinAdaptationCriterion> criteria =
                List.of(
                    new QuickInsulinAdaptationCriterion(null, 0.8, 0),
                    new QuickInsulinAdaptationCriterion(1.0, null, 0, true, false, false)
                );

        var expectedMessage = String.format(QuickInsulinCriteriaValidator.MSG_NOT_CONSECUTIVE_INTERVALS, 1.0, 0.8);

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(
                        () -> validator.validate(criteria)
                ).withMessage(expectedMessage);

    }

    @Test
    void testValidate_invalid_firstCriterionWithNullMin() {

        List<QuickInsulinAdaptationCriterion> criteria =
                List.of(
                        new QuickInsulinAdaptationCriterion(0.1, 0.8, 0, true, false, false),
                        new QuickInsulinAdaptationCriterion(0.8, null, 0)
                );

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(
                        () -> validator.validate(criteria)
                ).withMessage(QuickInsulinCriteriaValidator.MSG_MIN_VALUE_FIRST_CRITERION);

    }

    @Test
    void testValidate_invalid_lastCriterionWithNullMax() {

        List<QuickInsulinAdaptationCriterion> criteria =
                List.of(
                        new QuickInsulinAdaptationCriterion(null, 0.8, 0, true, false, false),
                        new QuickInsulinAdaptationCriterion(0.8, 2.1, 0)
                );

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(
                        () -> validator.validate(criteria)
                ).withMessage(QuickInsulinCriteriaValidator.MSG_MAX_VALUE_LAST_CRITERION);

    }

    @Test
    void testValidate_invalid_multipleIssues() {

        List<QuickInsulinAdaptationCriterion> criteria =
                List.of(
                        new QuickInsulinAdaptationCriterion(1.0, 0.8, 0),
                        new QuickInsulinAdaptationCriterion(0.8, 2.1, 0)
                );

        QuickInsulinCriteriaValidator validator = new QuickInsulinCriteriaValidator();

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(
                        () -> validator.validate(criteria)
                ).withMessageContaining(QuickInsulinCriteriaValidator.MSG_MIN_VALUE_FIRST_CRITERION)
                .withMessageContaining(QuickInsulinCriteriaValidator.MSG_MAX_VALUE_LAST_CRITERION)
                .withMessageContaining(QuickInsulinCriteriaValidator.MSG_MISSING_OBJECTIVE_CRITERION);

    }

}
