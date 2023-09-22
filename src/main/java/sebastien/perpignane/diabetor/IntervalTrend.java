package sebastien.perpignane.diabetor;

public enum TendanceIntervalle {
    HAUSSE(2),
    BAISSE(-2),
    STAGNANT(0);

    private final int adaptation;

    TendanceIntervalle(int adaptation) {
        this.adaptation = adaptation;
    }

    public int getAdaptation() {
        return adaptation;
    }
}
