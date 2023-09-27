package sebastien.perpignane.diabetor.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import sebastien.perpignane.diabetor.quickinsulin.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.Callable;

import static java.lang.System.*;

@CommandLine.Command(name = "punctual")
public class PunctualQuickInsulinCmd implements Callable<Integer> {

    private static final Scanner scanner = new Scanner(in);

    private  static final Logger log = LoggerFactory.getLogger(PunctualQuickInsulinCmd.class);

    @SuppressWarnings("java:S106")
    @Override
    public Integer call() {
        try {

            PunctualQuickInsulinAdaptationResult punctualQuickInsulinAdaptationResult
                    = manageAdaptation();

            DecimalFormat fmt = new DecimalFormat("+#;-#");

            String endOfMealMessage = "";
            if (punctualQuickInsulinAdaptationResult.endOfMeal()) {
                endOfMealMessage = System.lineSeparator() + "Take your insulin at the end of the meal";
            }

            System.out.printf(
                    """
                    Details:
                    \tGlycemia adaptation: %s
                    \tAcetone adaptation: %s
                    
                    Adaptation: %s
                    %s
                    """,
                    fmt.format(punctualQuickInsulinAdaptationResult.glycemiaAdaptation()),
                    fmt.format(punctualQuickInsulinAdaptationResult.acetoneAdaptation()),
                    fmt.format(punctualQuickInsulinAdaptationResult.getAdaptation()),
                    endOfMealMessage
            );

            return 0;
        }
        catch(Exception e) {
            log.error("Unexpected error while running punctual quick insulin command", e);
            return 1;
        }

    }

    private PunctualQuickInsulinAdaptationResult manageAdaptation() throws IOException {

        QuickInsulin quickInsulin =
                new QuickInsulin(
                        new QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl(),
                        new QuickInsulinAdaptationRepositoryJSonFileImpl()
                );

        out.println("Please enter glycemia:");

        double glycemiaBeforeMeal = Double.parseDouble(scanner.nextLine());

        try {
            return quickInsulin.computePunctualAdaptation(glycemiaBeforeMeal);
        }
        catch(AcetoneLevelRequiredException qie) {
            out.println("Measure acetone");
            int acetoneLevel = scanner.nextInt();
            return quickInsulin.computePunctualAdaptation(glycemiaBeforeMeal, acetoneLevel);
        }
    }

}
