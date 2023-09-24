package sebastien.perpignane.diabetor.quickinsulin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    void computeAdaptation() {

        QuickInsulinAdaptationCriteriaRepository repository = mock(QuickInsulinAdaptationCriteriaRepository.class);

        double glycemia = 0.8;

        when(repository.findAll()).thenReturn(criteria);

        QuickInsulin quickInsulin = new QuickInsulin(repository);

        var result = quickInsulin.computeAdaptation(glycemia);

        assertThat(result).isNotNull();

        assertThat(result.getAdaptation()).isZero();

    }

}