package sebastien.perpignane.diabetor.quickinsulin;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class QuickInsulinAdaptationCriteriaRepositoryJSonFileImplTest {

    @Test
    void testFindAll_defaultFile() throws IOException {

        QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl repo =
                new QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl();

        var results = repo.findAll();

        // Just check that the default file is loaded and is not empty, no assumption on its content
        assertThat(results)
                .isNotNull()
                .isNotEmpty();

    }

    @Test
    void testFindAll_testFile() throws IOException {

        String testJsonConfigFile = "quick_insulin_conditions_test.json";

        QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl repo =
                new QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl(
                    testJsonConfigFile
                );

        var results = repo.findAll();

        assertThat(results)
                .isNotNull()
                .isNotEmpty()
                .hasSize(6);

        assertThat(
            results.stream().filter(QuickInsulinAdaptationCriterion::isObjective).toList()
        ).hasSize(1);

    }

    @Test
    void testNonExistingConfigFile() {
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(
            () ->  new QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl("notExistsFile.json")
        );
    }

}
