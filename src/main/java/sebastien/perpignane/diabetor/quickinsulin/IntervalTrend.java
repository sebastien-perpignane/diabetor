package sebastien.perpignane.diabetor.quickinsulin;

public enum IntervalTrend {

    //FIXME make adaptation configurable
    UP(+2),
    DOWN(-2),
    STABLE(0);

    private final int adaptation;

    IntervalTrend(int adaptation) {
        this.adaptation = adaptation;
    }

    public int getAdaptation() {
        return adaptation;
    }
}
