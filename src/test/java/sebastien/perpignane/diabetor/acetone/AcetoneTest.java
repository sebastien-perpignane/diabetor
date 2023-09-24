package sebastien.perpignane.diabetor.acetone;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AcetoneTest {

    private static final List<AcetoneCriterion> criteria =
            List.of(
                    new AcetoneCriterion(1, 4),
                    new AcetoneCriterion(2, 10),
                    new AcetoneCriterion(3, 22)
            );

    @Test
    void testComputeAdaptation() {

        AcetoneCriterionRepository repository = mock(AcetoneCriterionRepository.class);

        when(repository.findAll()).thenReturn(criteria);

        Acetone acetone = new Acetone(repository);

        int adaptation = acetone.computeAdaptation(2);

        assertThat(adaptation).isEqualTo(10);

    }

    @Test
    void testComputeAdaptation_unknownLevel() {

        AcetoneCriterionRepository repository = mock(AcetoneCriterionRepository.class);

        when(repository.findAll()).thenReturn(criteria);

        Acetone acetone = new Acetone(repository);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> acetone.computeAdaptation(20)
        );

    }

}