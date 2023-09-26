package sebastien.perpignane.diabetor.quickinsulin;

public class QuickInsulinAdaptation {

    private String trend;

    private int adaptation;

    @SuppressWarnings("unused") // used by jackson
    QuickInsulinAdaptation() {}

    public QuickInsulinAdaptation(String trend, int adaptation) {
        this.trend = trend;
        this.adaptation = adaptation;
    }

    public String getTrend() {
        return trend;
    }

    public int getAdaptation() {
        return adaptation;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setTrend(String trend) {
        this.trend = trend;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setAdaptation(int adaptation) {
        this.adaptation = adaptation;
    }
}
