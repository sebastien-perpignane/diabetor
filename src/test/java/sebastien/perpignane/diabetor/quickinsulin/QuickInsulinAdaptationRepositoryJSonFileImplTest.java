package sebastien.perpignane.diabetor.quickinsulin;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class QuickInsulinAdaptationRepositoryJSonFileImplTest {

    @Test
    void testFindAll_defaultFile() throws IOException {

        QuickInsulinAdaptationRepositoryJSonFileImpl repository =
                new QuickInsulinAdaptationRepositoryJSonFileImpl();

        List<QuickInsulinAdaptation> all = repository.findAll();

        assertThat(all)
                .isNotNull()
                .isNotEmpty();

    }

    @Test
    void testFindAll_testFile() throws IOException {

        QuickInsulinAdaptationRepositoryJSonFileImpl repository =
                new QuickInsulinAdaptationRepositoryJSonFileImpl("quick_insulin_adaptation_test.json");

        List<QuickInsulinAdaptation> all = repository.findAll();

        assertThat(all)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        var upAdaptation =
                all.stream()
                        .filter(qia -> IntervalTrend.UP.name().equals(qia.getTrend()))
                        .findFirst();

        var downAdaptation =
                all.stream()
                        .filter(qia -> IntervalTrend.DOWN.name().equals(qia.getTrend()))
                        .findFirst();

        var stableAdaptation =
                all.stream()
                        .filter(qia -> IntervalTrend.STABLE.name().equals(qia.getTrend()))
                        .findFirst();

        assertThat(upAdaptation)
            .isPresent();

        assertThat(downAdaptation)
            .isPresent();

        assertThat(stableAdaptation)
                .isPresent();

    }

    @Test
    void testFindAll_fileNotFound() {

        assertThatIllegalStateException().isThrownBy(
            () -> new QuickInsulinAdaptationRepositoryJSonFileImpl("quick_insulin_adaptation_bad.json")
        );

    }

}