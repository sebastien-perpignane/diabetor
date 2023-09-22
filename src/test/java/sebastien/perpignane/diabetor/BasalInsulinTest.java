package sebastien.perpignane.diabetor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class InsulineBasaleTest {

    @Test
    void testComputeAdaptation_allUp() {
        var interval1 = new GlycemiaInterval(1.2, 1.5);
        var interval2 = new GlycemiaInterval(1.4, 1.9);
        var interval3 = new GlycemiaInterval(1.1, 1.5);
        var intervals = List.of(
                interval1, interval2, interval3
        );

        InsulineBasale insulineBasale = new InsulineBasale();
        int result = insulineBasale.computeAdaptation(intervals);

        assertThat(result).isEqualTo(2);

    }

    @Test
    void testComputeAdaptation_allUp_notAllSignificant() {
        var interval1 = new GlycemiaInterval(1.2, 1.3); // not significant increase
        var interval2 = new GlycemiaInterval(1.4, 1.9);
        var interval3 = new GlycemiaInterval(1.1, 1.5);
        var intervals = List.of(
                interval1, interval2, interval3
        );

        InsulineBasale insulineBasale = new InsulineBasale();
        int result = insulineBasale.computeAdaptation(intervals);

        assertThat(result).isZero();

    }

    @Test
    void testComputeAdaptation_allDown() {
        var interval1 = new GlycemiaInterval(1.5, 1.2);
        var interval2 = new GlycemiaInterval(1.9, 1.4);
        var interval3 = new GlycemiaInterval(1.5, 1.1);
        var intervals = List.of(
                interval1, interval2, interval3
        );

        InsulineBasale insulineBasale = new InsulineBasale();
        int result = insulineBasale.computeAdaptation(intervals);

        assertThat(result).isEqualTo(-2);

    }

    @Test
    void testComputeAdaptation_allDown_notAllSignificant() {
        var interval1 = new GlycemiaInterval(1.5, 1.2);
        var interval2 = new GlycemiaInterval(1.9, 1.4);
        var interval3 = new GlycemiaInterval(1.5, 1.3);
        var intervals = List.of(
                interval1, interval2, interval3
        );

        InsulineBasale insulineBasale = new InsulineBasale();
        int result = insulineBasale.computeAdaptation(intervals);

        assertThat(result).isZero();

    }

    @Test
    void testComputeAdaptation_upAndDown() {
        var interval1 = new GlycemiaInterval(1.5, 1.2);
        var interval2 = new GlycemiaInterval(1.4, 1.8);
        var interval3 = new GlycemiaInterval(1.5, 1.1);
        var intervals = List.of(
            interval1, interval2, interval3
        );

        InsulineBasale insulineBasale = new InsulineBasale();
        int result = insulineBasale.computeAdaptation(intervals);

        assertThat(result).isZero();

    }

    @Test
    void testComputeAdaptation_notEnoughIntervals() {
        var interval1 = new GlycemiaInterval(1.5, 1.2);
        var interval2 = new GlycemiaInterval(1.9, 1.4);
        var intervals = List.of(
            interval1, interval2
        );

        InsulineBasale insulineBasale = new InsulineBasale();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
            () -> insulineBasale.computeAdaptation(intervals)
        );

    }

}
