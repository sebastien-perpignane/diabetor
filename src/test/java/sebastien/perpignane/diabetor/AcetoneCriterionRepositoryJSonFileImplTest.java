package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AcetoneCriterionRepositoryJSonFileImplTest {

    @Test
    void testFindAll_defaultFile() throws URISyntaxException, IOException {

        AcetoneCriterionRepositoryJSonFileImpl repo = new AcetoneCriterionRepositoryJSonFileImpl();

        List<AcetoneCriterion> results = repo.findAll();

        assertThat(results)
                .isNotNull()
                .isNotEmpty();

    }

    @Test
    void testFindAll_testFile() throws URISyntaxException, IOException {

        String testConfigFile = "acetone_test.json";

        AcetoneCriterionRepositoryJSonFileImpl repo = new AcetoneCriterionRepositoryJSonFileImpl(testConfigFile);

        List<AcetoneCriterion> results = repo.findAll();

        assertThat(results)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4);

        var firstCriterion = results.get(0);

        assertThat(firstCriterion.getLevel()).isEqualTo(1);
        assertThat(firstCriterion.getAdaptation()).isEqualTo(4);

        var secondCriterion = results.get(1);

        assertThat(secondCriterion.getLevel()).isEqualTo(2);
        assertThat(secondCriterion.getAdaptation()).isEqualTo(6);

        var thirdCriterion = results.get(2);

        assertThat(thirdCriterion.getLevel()).isEqualTo(3);
        assertThat(thirdCriterion.getAdaptation()).isEqualTo(8);

        var fourthCriterion = results.get(3);

        assertThat(fourthCriterion.getLevel()).isEqualTo(4);
        assertThat(fourthCriterion.getAdaptation()).isEqualTo(12);

    }

    @Test
    void testInvalidConfigFile() {
        String invalidConfigFile = "acetone_bad.json";

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(
            () -> new AcetoneCriterionRepositoryJSonFileImpl(invalidConfigFile)
        );
    }

}
