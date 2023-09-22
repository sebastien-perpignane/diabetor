package sebastien.perpignane.diabetor;

public class QuickInsulin {

    private final QuickInsulinAdaptationCriteriaRepository repository;

    public QuickInsulin(QuickInsulinAdaptationCriteriaRepository repository) {
        this.repository = repository;
    }

    QuickInsulinAdaptationCriterion computeAdaptation(double glycemia) {
        return repository.findAll().stream().filter(c -> c.isIncluded(glycemia)).findFirst().orElseThrow();
    }

}
