package sebastien.perpignane.diabetor.basal;

public enum IntervalTrend {
    INCREASE(2),
    DECREASE(-2),
    STAGNATION(0);

    private final int adaptation;

    IntervalTrend(int adaptation) {
        this.adaptation = adaptation;
    }

    public int getAdaptation() {
        return adaptation;
    }
}
