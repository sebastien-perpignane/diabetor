package sebastien.perpignane.diabetor.basal;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BasalInsulinTest {

    @Test
    void testComputeAdaptation_allSignificantUp() {
        var interval1 = new NightGlycemiaInterval(1.2, 1.5); // +0.3
        var interval2 = new NightGlycemiaInterval(1.4, 1.9); // +0.5
        var interval3 = new NightGlycemiaInterval(1.1, 1.5); // +0.4
        var intervals = List.of(
            interval1, interval2, interval3
        );

        BasalInsulin basalInsulin = new BasalInsulin();
        int result = basalInsulin.computeAdaptation(intervals);

        assertThat(result).isEqualTo(2);

    }

    @Test
    void testComputeAdaptation_allUp_notAllSignificant() {
        var interval1 = new NightGlycemiaInterval(1.2, 1.3); // +0.1
        var interval2 = new NightGlycemiaInterval(1.4, 1.9); // +0.5
        var interval3 = new NightGlycemiaInterval(1.1, 1.5); // +0.4
        var intervals = List.of(
            interval1, interval2, interval3
        );

        BasalInsulin basalInsulin = new BasalInsulin();
        int result = basalInsulin.computeAdaptation(intervals);

        assertThat(result).isZero();

    }

    @Test
    void testComputeAdaptation_allSignificantDown() {
        var interval1 = new NightGlycemiaInterval(1.5, 1.2); // -0.3
        var interval2 = new NightGlycemiaInterval(1.9, 1.4); // -0.5
        var interval3 = new NightGlycemiaInterval(1.5, 1.1); // -0.4
        var intervals = List.of(
                interval1, interval2, interval3
        );

        BasalInsulin basalInsulin = new BasalInsulin();
        int result = basalInsulin.computeAdaptation(intervals);

        assertThat(result).isEqualTo(-2);

    }

    @Test
    void testComputeAdaptation_allDown_notAllSignificant() {
        var interval1 = new NightGlycemiaInterval(1.5, 1.2); // -0.3
        var interval2 = new NightGlycemiaInterval(1.9, 1.4); // -0.5
        var interval3 = new NightGlycemiaInterval(1.5, 1.3); // -0.2
        var intervals = List.of(
            interval1, interval2, interval3
        );

        BasalInsulin basalInsulin = new BasalInsulin();
        int result = basalInsulin.computeAdaptation(intervals);

        assertThat(result).isZero();

    }

    @Test
    void testComputeAdaptation_significantUpAndSignificantDown() {
        var interval1 = new NightGlycemiaInterval(1.5, 1.2); // -0.3
        var interval2 = new NightGlycemiaInterval(1.4, 1.8); // +0.4
        var interval3 = new NightGlycemiaInterval(1.5, 1.1); // -0.4
        var intervals = List.of(
            interval1, interval2, interval3
        );

        BasalInsulin basalInsulin = new BasalInsulin();
        int result = basalInsulin.computeAdaptation(intervals);

        assertThat(result).isZero();

    }

    @Test
    void testComputeAdaptation_noSignificantDelta() {
        var interval1 = new NightGlycemiaInterval(1.5, 1.4); // -0.1
        var interval2 = new NightGlycemiaInterval(1.4, 1.6); // +0.2
        var interval3 = new NightGlycemiaInterval(1.5, 1.6); // +0.1
        var intervals = List.of(
                interval1, interval2, interval3
        );

        BasalInsulin basalInsulin = new BasalInsulin();
        int result = basalInsulin.computeAdaptation(intervals);

        assertThat(result).isZero();

    }

    @Test
    void testComputeAdaptation_alwaysEquals() {
        var interval1 = new NightGlycemiaInterval(1.5, 1.5); // +0
        var interval2 = new NightGlycemiaInterval(1.4, 1.4); // +0
        var interval3 = new NightGlycemiaInterval(1.2, 1.2); // +0
        var intervals = List.of(
                interval1, interval2, interval3
        );

        BasalInsulin basalInsulin = new BasalInsulin();
        int result = basalInsulin.computeAdaptation(intervals);

        assertThat(result).isZero();

    }

    @Test
    void testComputeAdaptation_notEnoughIntervals() {
        var interval1 = new NightGlycemiaInterval(1.5, 1.2);
        var interval2 = new NightGlycemiaInterval(1.9, 1.4);
        var intervals = List.of(
            interval1, interval2
        );

        BasalInsulin basalInsulin = new BasalInsulin();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
            () -> basalInsulin.computeAdaptation(intervals)
        );

    }

}
