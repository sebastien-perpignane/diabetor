package sebastien.perpignane.diabetor.basal;

import sebastien.perpignane.diabetor.common.GlycemiaInterval;

public class NightGlycemiaInterval {

    public static final String SIGNIFICANT_DELTA_PROPERTY = "significantDelta";

    private final GlycemiaInterval glycemiaInterval;

    public NightGlycemiaInterval(double beforeSleepingGlycemia, double wakeUpGlycemia) {
        this.glycemiaInterval = new GlycemiaInterval(beforeSleepingGlycemia, wakeUpGlycemia);
    }

    public IntervalTrend getTrend() {

        double delta = glycemiaInterval.getDelta();

        if (delta > getSignificantDelta()) {
            return IntervalTrend.INCREASE;
        }
        if (delta < -getSignificantDelta()) {
            return IntervalTrend.DECREASE;
        }

        return IntervalTrend.STAGNATION;

    }

    private double getSignificantDelta() {
        return Double.parseDouble(System.getProperty(SIGNIFICANT_DELTA_PROPERTY, "0.3"));
    }

}
