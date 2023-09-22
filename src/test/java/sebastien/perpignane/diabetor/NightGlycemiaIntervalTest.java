package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GlycemiaIntervalTest {

    @BeforeAll
    static void globalSetup() {
        System.setProperty("significantDelta", "0.3");
    }

    @DisplayName("Trend is computed as expected depending on the first and second glycemia measures")
    @ParameterizedTest(name = "when glycemia before sleeping = {0} and glycemia at wake-up = {1}, {3} (expected trend: {2})")
    @MethodSource("testTrendData")
    void testTrends(double glycemia1, double glycemia2, IntervalTrend expectedTrend, String ignoredLabel) {
        var interval = new NightGlycemiaInterval(glycemia1, glycemia2);

        assertThat(interval.getTrend()).isEqualTo(expectedTrend);
    }

    static Stream<Arguments> testTrendData() {
        return Stream.of(
            Arguments.of(1.2, 1.5, IntervalTrend.INCREASE, "significant increase trend"),
            Arguments.of(1.2, 1.4, IntervalTrend.STAGNATION, "no significant increase trend"),
            Arguments.of(1.2, 0.8, IntervalTrend.DECREASE, "significant decrease trend"),
            Arguments.of(1.2, 1.0, IntervalTrend.STAGNATION, "no significant decrease trend"),
            Arguments.of(1.2, 1.2, IntervalTrend.STAGNATION, "stagnation")
        );
    }

}
