package sebastien.perpignane.diabetor.common;

public class GlycemiaInterval {

    private final double beforeGlycemia;

    private final double afterGlycemia;

    private final double delta;

    public GlycemiaInterval(double beforeGlycemia, double afterGlycemia) {
        this.beforeGlycemia = beforeGlycemia;
        this.afterGlycemia = afterGlycemia;
        this.delta = afterGlycemia - beforeGlycemia;
    }

    public double getBeforeGlycemia() {
        return beforeGlycemia;
    }

    public double getAfterGlycemia() {
        return afterGlycemia;
    }

    public double getDelta() {
        return delta;
    }

}
