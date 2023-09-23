package sebastien.perpignane.diabetor;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.ValueIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcetoneCriterionRepositoryJSonFileImpl implements AcetoneCriterionRepository {

    private final List<AcetoneCriterion> criteria;

    public AcetoneCriterionRepositoryJSonFileImpl() throws IOException {
        this("acetone.json");
    }

    public AcetoneCriterionRepositoryJSonFileImpl(String jsonConfigFile) throws IOException {

        var is = getClass().getResourceAsStream(jsonConfigFile);

        if (is == null) {
            throw new IllegalStateException("Error while loading quick insulin adaptation criteria");
        }

        try (ValueIterator<AcetoneCriterion> it = JSON.std.with(JSON.Feature.WRITE_NULL_PROPERTIES).beanSequenceFrom(AcetoneCriterion.class, is)) {

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
