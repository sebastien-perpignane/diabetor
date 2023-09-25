package sebastien.perpignane.diabetor.quickinsulin;

import java.util.ArrayList;
import java.util.List;

public class QuickInsulinCriteriaValidator {

    public static final String MSG_NOT_NULL_CRITERIA = "criteria must not be null";

    public static final String MSG_NB_CRITERIA = "At least 2 criteria are required";

    public static final String MSG_MIN_VALUE_FIRST_CRITERION = "Min value of the first criterion must be empty";

    public static final String MSG_MAX_VALUE_LAST_CRITERION = "Max value of the last criterion must be empty";

    public static final String MSG_MISSING_OBJECTIVE_CRITERION = "There must be one and only one criterion defined as objective";

    public static final String MSG_NOT_CONSECUTIVE_INTERVALS =
            "Criterion with min value %f is not valid. Its min value should be %f, the max value of the previous criterion";

    void validate(List<QuickInsulinAdaptationCriterion> criteria) throws ValidationException {

        List<String> validationMessages = new ArrayList<>();

        if (criteria == null) {
            throw new IllegalArgumentException(MSG_NOT_NULL_CRITERIA);
        }

        int criteriaSize = criteria.size();

        if (criteriaSize < 2) {
            throw new IllegalArgumentException(MSG_NB_CRITERIA);
        }

        var firstCriterion = criteria.get(0);
        var lastCriterion = criteria.get(criteriaSize - 1);

        if (firstCriterion.getMin() != Double.MIN_VALUE) {
            validationMessages.add(MSG_MIN_VALUE_FIRST_CRITERION);
        }

        if (lastCriterion.getMax() != Double.MAX_VALUE) {
            validationMessages.add(MSG_MAX_VALUE_LAST_CRITERION);
        }

        QuickInsulinAdaptationCriterion previous = null;

        int nbCriteriaAsObjective = 0;

        for (var criterion : criteria) {

            if (criterion.isObjective()) {
                nbCriteriaAsObjective++;
            }

            if (previous == null) {
                previous = criterion;
                continue;
            }

            if ( !criterion.getMin().equals(previous.getMax()) ) {
                validationMessages.add(
                    String.format(
                            MSG_NOT_CONSECUTIVE_INTERVALS,
                            criterion.getMin(),
                            previous.getMax()
                    )
                );
            }

            previous = criterion;

        }

        if (nbCriteriaAsObjective != 1) {
            validationMessages.add(MSG_MISSING_OBJECTIVE_CRITERION);
        }

        if (!validationMessages.isEmpty()) {
            throw new ValidationException(validationMessages);
        }

    }

}
