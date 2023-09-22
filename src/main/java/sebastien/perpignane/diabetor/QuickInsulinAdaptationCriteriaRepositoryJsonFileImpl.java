package sebastien.perpignane.diabetor;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.ValueIterator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl implements QuickInsulinAdaptationCriteriaRepository {

    private final List<QuickInsulinAdaptationCriterion> criteria;

    public QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl() throws URISyntaxException, IOException {
        this("quick_insulin_intervals.json");
    }

    QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl(String jsonConfigFile) throws URISyntaxException, IOException {

        URL url = getClass().getResource(jsonConfigFile);

        if (url == null) {
            throw new IllegalStateException("Error while loading quick insulin adaptation criteria");
        }

        File configFile = new File(url.toURI());

        try (ValueIterator<QuickInsulinAdaptationCriterion> it = JSON.std.with(JSON.Feature.WRITE_NULL_PROPERTIES).beanSequenceFrom(QuickInsulinAdaptationCriterion.class, configFile)) {

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
