package sebastien.perpignane.diabetor;

public class AcetoneCriterion {

    private int level;

    private int adaptation;

    @SuppressWarnings("unused") // used by jackson
    AcetoneCriterion() {
    }

    public AcetoneCriterion(int level, int adaptation) {
        this.level = level;
        this.adaptation = adaptation;
    }

    public int getLevel() {
        return level;
    }

    public int getAdaptation() {
        return adaptation;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setLevel(int level) {
        this.level = level;
    }

    @SuppressWarnings("unused") // used by jackson
    private void setAdaptation(int adaptation) {
        this.adaptation = adaptation;
    }

}
