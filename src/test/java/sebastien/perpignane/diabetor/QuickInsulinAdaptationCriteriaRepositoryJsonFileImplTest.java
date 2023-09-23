package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.*;

class QuickInsulinAdaptationCriteriaRepositoryJsonFileImplTest {

    @Test
    void testFindAll_defaultFile() throws URISyntaxException, IOException {

        QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl repo =
                new QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl();

        var results = repo.findAll();

        // Just check that the default file is loaded and is not empty, no assumption on its content
        assertThat(results)
                .isNotNull()
                .isNotEmpty();

    }

    @Test
    void testFindAll_testFile() throws URISyntaxException, IOException {

        String testJsonConfigFile = "quick_insulin_conditions_test.json";

        QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl repo =
                new QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl(
                    testJsonConfigFile
                );

        var results = repo.findAll();

        assertThat(results)
                .isNotNull()
                .isNotEmpty()
                .hasSize(6);

    }

    @Test
    void testNonExistingConfigFile() {
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(
            () ->  new QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl("notExistsFile.json")
        );
    }

}
