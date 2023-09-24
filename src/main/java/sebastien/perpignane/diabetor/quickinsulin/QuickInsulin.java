package sebastien.perpignane.diabetor.quickinsulin;

public class QuickInsulin {

    private final QuickInsulinAdaptationCriteriaRepository repository;

    public QuickInsulin(QuickInsulinAdaptationCriteriaRepository repository) {
        this.repository = repository;
    }

    public QuickInsulinAdaptationCriterion computeAdaptation(double glycemia) {
        return repository.findAll().stream().filter(c -> c.isIncluded(glycemia)).findFirst().orElseThrow();
    }

}
