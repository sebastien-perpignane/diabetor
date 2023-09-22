package sebastien.perpignane.diabetor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InsulineBasale {

    public int computeAdaptation(List<GlycemiaInterval> intervallesGlycemie) {

        int nbIntervals = intervallesGlycemie.size();

        if (nbIntervals != 3) {
            throw new IllegalArgumentException("Nombre d'intervalles incorrect. Attendus: 3. Reçus: " + nbIntervals);
        }

        Set<IntervalTrend> trends = intervallesGlycemie.stream().map(GlycemiaInterval::getTrend).collect(Collectors.toSet());

        if (trends.size() == 1) {
            return trends.iterator().next().getAdaptation();
        }

        return 0;

    }

}

