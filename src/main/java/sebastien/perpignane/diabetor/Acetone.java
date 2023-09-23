package sebastien.perpignane.diabetor;

import java.util.Map;
import java.util.stream.Collectors;

public class Acetone {

    private final Map<Integer, Integer> acetoneAdaptationByLevel;

    public Acetone(AcetoneCriterionRepository repository) {
        acetoneAdaptationByLevel = repository.findAll().stream().collect(Collectors.toMap(AcetoneCriterion::getLevel, AcetoneCriterion::getAdaptation));
    }

    int computeAdaptation(int acetoneLevel) {
        if (!acetoneAdaptationByLevel.containsKey(acetoneLevel)) {
            throw new IllegalArgumentException(String.format("Unknown acetone level: %d", acetoneLevel));
        }
        return acetoneAdaptationByLevel.get(acetoneLevel);
    }

}
