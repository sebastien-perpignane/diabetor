package sebastien.perpignane.diabetor.common;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GlycemiaIntervalTest {

    @Test
    void testConstructor() {
        double beforeGlycemia = 1.2;
        double afterGlycemia = 1.8;

        var glycemiaInterval = new GlycemiaInterval(beforeGlycemia, afterGlycemia);

        assertThat(glycemiaInterval.getBeforeGlycemia()).isEqualTo(beforeGlycemia);
        assertThat(glycemiaInterval.getAfterGlycemia()).isEqualTo(afterGlycemia);
    }

    @Test
    void testGetDelta() {

        double beforeGlycemia = 1.2;
        double afterGlycemia = 1.8;

        var glycemiaInterval = new GlycemiaInterval(beforeGlycemia, afterGlycemia);

        assertThat(glycemiaInterval.getDelta()).isCloseTo(0.6d, Percentage.withPercentage(0.001));

    }

}
