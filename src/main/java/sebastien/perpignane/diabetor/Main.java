package sebastien.perpignane.diabetor;

import sebastien.perpignane.diabetor.quickinsulin.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;


public class Main {

    private static final Scanner scanner = new Scanner(in);

    public static void main(String[] args) throws IOException {

        DecimalFormat fmt = new DecimalFormat("+#;-#");

        out.println("Please enter glycemia:");

        double glycemiaBeforeMeal = Double.parseDouble(scanner.nextLine());

        QuickInsulin quickInsulin =
                new QuickInsulin(
                    new QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl(),
                    new QuickInsulinAdaptationRepositoryJSonFileImpl()
                );

        PunctualQuickInsulinAdaptationResult punctualQuickInsulinAdaptationResult;
        try {
            punctualQuickInsulinAdaptationResult = quickInsulin.computePunctualAdaptation(glycemiaBeforeMeal);
        }
        catch(AcetoneLevelRequiredException qie) {
            out.println("Measure acetone");
            int acetoneLevel = scanner.nextInt();
            punctualQuickInsulinAdaptationResult = quickInsulin.computePunctualAdaptation(glycemiaBeforeMeal, acetoneLevel);
        }

        String endOfMealMessage = "";
        if (punctualQuickInsulinAdaptationResult.isEndOfMeal()) {
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
            fmt.format(punctualQuickInsulinAdaptationResult.getGlycemiaAdaptation()),
            fmt.format(punctualQuickInsulinAdaptationResult.getAcetoneAdaptation()),
            fmt.format(punctualQuickInsulinAdaptationResult.getAdaptation()),
            endOfMealMessage
        );

    }
}
