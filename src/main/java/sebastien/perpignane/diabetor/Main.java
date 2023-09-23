package sebastien.perpignane.diabetor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;


public class Main {

    private static final Scanner scanner = new Scanner(in);

    public static void main(String[] args) throws IOException, URISyntaxException {

        DecimalFormat fmt = new DecimalFormat("+#;-#");

        out.println("Please enter glycemia:");

        double glycemiaBeforeMeal = Double.parseDouble(scanner.nextLine());

        QuickInsulin quickInsulin = new QuickInsulin(new QuickInsulinAdaptationCriteriaRepositoryJsonFileImpl());

        var adaptation = quickInsulin.computeAdaptation(glycemiaBeforeMeal);

        int acetoneAdaptation = 0;
        if (adaptation.isCheckAcetone()) {
            out.println("Measure acetone");
            int acetoneLevel = scanner.nextInt();
            Acetone acetone = new Acetone(new AcetoneCriterionRepositoryJSonFileImpl());
            acetoneAdaptation = acetone.computeAdaptation(acetoneLevel);
        }

        int totalAdaptation = adaptation.getAdaptation() + acetoneAdaptation;

        System.out.println("Adaptation: " + fmt.format(totalAdaptation));

        if (adaptation.isEndOfMeal()) {
            System.out.println("Take your insulin at the end of the meal");
        }

    }
}
