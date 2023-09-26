package sebastien.perpignane.diabetor.quickinsulin;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.ValueIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuickInsulinAdaptationRepositoryJSonFileImpl implements QuickInsulinAdaptationRepository {

    private final List<QuickInsulinAdaptation> adaptations;

    public QuickInsulinAdaptationRepositoryJSonFileImpl() throws IOException {
        this("quick_insulin_adaptation.json");
    }

    public QuickInsulinAdaptationRepositoryJSonFileImpl(String jsonConfigFile) throws IOException {
        var is = getClass().getResourceAsStream(jsonConfigFile);

        if (is == null) {
            throw new IllegalStateException("Error while loading quick insulin adaptation criteria");
        }

        try (ValueIterator<QuickInsulinAdaptation> it = JSON.std.with(JSON.Feature.WRITE_NULL_PROPERTIES).beanSequenceFrom(QuickInsulinAdaptation.class, is)) {

            adaptations = new ArrayList<>();

            QuickInsulinAdaptation adaptation;
            while(it.hasNextValue()) {
                adaptation = it.nextValue();
                adaptations.add(adaptation);
            }
        }
    }

    @Override
    public List<QuickInsulinAdaptation> findAll() {
        return adaptations;
    }
}
