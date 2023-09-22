package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NightGlycemiaIntervalTest {

    @AfterAll
    static void cleanConfig() {
        System.clearProperty(NightGlycemiaInterval.SIGNIFICANT_DELTA_PROPERTY);
    }

    @DisplayName("Trend is computed as expected depending on the first and second glycemia measures")
    @ParameterizedTest(name = "significant delta = {0}. When glycemia before sleeping = {1} and glycemia at wake-up = {2}, {4} (expected trend: {3})")
    @MethodSource("testTrendData")
    void testTrends(double significantDelta, double glycemia1, double glycemia2, IntervalTrend expectedTrend, String ignoredLabel) {

        defineSignificantDelta(significantDelta);

        var interval = new NightGlycemiaInterval(glycemia1, glycemia2);

        assertThat(interval.getTrend())
                .isEqualTo(expectedTrend);
    }

    private void defineSignificantDelta(double significantDelta) {
        System.setProperty("significantDelta", Double.valueOf(significantDelta).toString());
    }

    static Stream<Arguments> testTrendData() {
        return Stream.of(
            // significant delta = 0.3
            Arguments.of(0.3, 1.2, 1.5, IntervalTrend.INCREASE, "significant increase trend"),
            Arguments.of(0.3, 1.2, 1.4, IntervalTrend.STAGNATION, "no significant increase trend"),
            Arguments.of(0.3, 1.2, 0.8, IntervalTrend.DECREASE, "significant decrease trend"),
            Arguments.of(0.3, 1.2, 1.0, IntervalTrend.STAGNATION, "no significant decrease trend"),
            Arguments.of(0.3, 1.2, 1.2, IntervalTrend.STAGNATION, "stagnation"),
            // significant delta = 0.5
            Arguments.of(0.5, 1.2, 1.8, IntervalTrend.INCREASE, "significant increase trend"),
            Arguments.of(0.5, 1.2, 1.4, IntervalTrend.STAGNATION, "no significant increase trend"),
            Arguments.of(0.5, 1.2, 0.6, IntervalTrend.DECREASE, "significant decrease trend"),
            Arguments.of(0.5, 1.2, 1.0, IntervalTrend.STAGNATION, "no significant decrease trend"),
            Arguments.of(0.5, 1.2, 1.2, IntervalTrend.STAGNATION, "stagnation")
        );
    }

}
