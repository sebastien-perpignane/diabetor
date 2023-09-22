package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.*;

class QuickInsulinAdaptationCriteriaRepositoryJsonFileImplTest {

    @Test
    void testFindAll() throws URISyntaxException, IOException {

        String testJsonConfigFile = "quick_insulin_intervals_test.json";

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
