package sebastien.perpignane.diabetor;

public class GlycemiaInterval {

    private static final double SIGNIFICANT_DELTA = Double.parseDouble(System.getProperty("significantDelta", "0.3"));

    private final double beforeSleepingGlycemia;

    private final double wakeUpGlycemia;

    private final double delta;

    public GlycemiaInterval(double beforeSleepingGlycemia, double wakeUpGlycemia) {
        this.beforeSleepingGlycemia = beforeSleepingGlycemia;
        this.wakeUpGlycemia = wakeUpGlycemia;
        delta = wakeUpGlycemia - beforeSleepingGlycemia;
    }

    public IntervalTrend getTrend() {

        if (beforeSleepingGlycemia == wakeUpGlycemia) {
            return IntervalTrend.STAGNATION;
        }

        if (delta > SIGNIFICANT_DELTA) {
            return IntervalTrend.INCREASE;
        }
        if (delta < -SIGNIFICANT_DELTA) {
            return IntervalTrend.DECREASE;
        }

        return IntervalTrend.STAGNATION;

    }

}

