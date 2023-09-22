package sebastien.perpignane.diabetor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasalInsulin {

    public int computeAdaptation(List<NightGlycemiaInterval> nightGlycemiaIntervals) {

        int nbIntervals = nightGlycemiaIntervals.size();

        if (nbIntervals != 3) {
            throw new IllegalArgumentException("Incorrect number of intervals. Expected: 3. Received: " + nbIntervals);
        }

        Set<IntervalTrend> trends = nightGlycemiaIntervals.stream().map(NightGlycemiaInterval::getTrend).collect(Collectors.toSet());

        if (trends.size() == 1) {
            return trends.iterator().next().getAdaptation();
        }

        return 0;

    }

}
