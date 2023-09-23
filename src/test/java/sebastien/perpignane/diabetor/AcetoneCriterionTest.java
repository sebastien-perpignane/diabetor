package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AcetoneCriterionTest {

    @Test
    void testConstructor() {

        int level = 12;
        int adaptation = 52;

        AcetoneCriterion criterion = new AcetoneCriterion(level, adaptation);

        assertThat(criterion.getLevel()).isEqualTo(level);
        assertThat(criterion.getAdaptation()).isEqualTo(adaptation);


    }

}