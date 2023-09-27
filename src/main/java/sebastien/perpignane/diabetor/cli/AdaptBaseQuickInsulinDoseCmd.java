package sebastien.perpignane.diabetor.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import sebastien.perpignane.diabetor.quickinsulin.MealGlycemiaMeasure;
import sebastien.perpignane.diabetor.quickinsulin.QuickInsulin;
import sebastien.perpignane.diabetor.quickinsulin.QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl;
import sebastien.perpignane.diabetor.quickinsulin.QuickInsulinAdaptationRepositoryJSonFileImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

import static java.lang.System.in;

@CommandLine.Command(name = "adapt-dose-quick")
public class AdaptBaseQuickInsulinDoseCmd implements Callable<Integer> {

    private static final Scanner scanner = new Scanner(in);

    private  static final Logger log = LoggerFactory.getLogger(AdaptBaseQuickInsulinDoseCmd.class);

    @SuppressWarnings("java:S106")
    @Override
    public Integer call() {

        try {
            System.out.println("Please enter 3 last glycemia levels measured after the meal");

            String glycemiaListStr = scanner.next();

            List<MealGlycemiaMeasure> mealGlycemiaMeasures =
                    Arrays.stream(glycemiaListStr.split(","))
                            .map(String::trim)
                            .map(d -> new MealGlycemiaMeasure(Double.parseDouble(d)))
                            .toList();
            QuickInsulin quickInsulin =
                    new QuickInsulin(
                        new QuickInsulinAdaptationCriteriaRepositoryJSonFileImpl(),
                        new QuickInsulinAdaptationRepositoryJSonFileImpl()
                    );

            int result = quickInsulin.computeLongTermAdaptation(mealGlycemiaMeasures);

            System.out.printf("Recommended adaptation is: %d%n", result);

            return 0;
        }
        catch(Exception e) {
            log.error("unexpected error while running adapt-base-quick subcommand", e);
            return 1;
        }
    }
}
