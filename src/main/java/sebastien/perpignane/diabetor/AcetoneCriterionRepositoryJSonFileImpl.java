package sebastien.perpignane.diabetor;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.ValueIterator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AcetoneCriterionRepositoryJSonFileImpl implements AcetoneCriterionRepository {

    private final List<AcetoneCriterion> criteria;

    public AcetoneCriterionRepositoryJSonFileImpl() throws URISyntaxException, IOException {
        this("acetone.json");
    }

    public AcetoneCriterionRepositoryJSonFileImpl(String jsonConfigFile) throws URISyntaxException, IOException {

        URL url = getClass().getResource(jsonConfigFile);

        if (url == null) {
            throw new IllegalStateException("Error while loading acetone adaptation criteria");
        }

        File configFile = new File(url.toURI());

        try (ValueIterator<AcetoneCriterion> it = JSON.std.with(JSON.Feature.WRITE_NULL_PROPERTIES).beanSequenceFrom(AcetoneCriterion.class, configFile)) {

            criteria = new ArrayList<>();

            AcetoneCriterion criterion;
            while(it.hasNextValue()) {
                criterion = it.nextValue();
                criteria.add(criterion);
            }
        }

    }

    @Override
    public List<AcetoneCriterion> findAll() {
        return criteria;
    }

}
