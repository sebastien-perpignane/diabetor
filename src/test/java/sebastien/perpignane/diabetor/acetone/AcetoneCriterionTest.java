package sebastien.perpignane.diabetor.acetone;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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