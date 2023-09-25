package sebastien.perpignane.diabetor.quickinsulin;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.ValueIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl implements QuickInsulinAdaptationCriteriaRepository {

    private final List<QuickInsulinAdaptationCriterion> criteria;

    public QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl() throws IOException {
        this("quick_insulin_conditions.json");
    }

    QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl(String jsonConfigFile) throws IOException {
        var is = getClass().getResourceAsStream(jsonConfigFile);

        if (is == null) {
            throw new IllegalStateException("Error while loading quick insulin adaptation criteria");
        }

        try (ValueIterator<QuickInsulinAdaptationCriterion> it = JSON.std.with(JSON.Feature.WRITE_NULL_PROPERTIES).beanSequenceFrom(QuickInsulinAdaptationCriterion.class, is)) {

            criteria = new ArrayList<>();

            QuickInsulinAdaptationCriterion criterion;
            while(it.hasNextValue()) {
                criterion = it.nextValue();
                criteria.add(criterion);
            }
        }

    }

    @Override
    public List<QuickInsulinAdaptationCriterion> findAll() {
        return criteria;
    }

}
