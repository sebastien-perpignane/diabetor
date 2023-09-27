package sebastien.perpignane.diabetor.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import sebastien.perpignane.diabetor.basal.BasalInsulin;
import sebastien.perpignane.diabetor.basal.NightGlycemiaInterval;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

import static java.lang.System.in;

@CommandLine.Command(name = "adapt-dose-basal")
public class AdaptBasalInsulinDoseCmd implements Callable<Integer> {

    private static final Scanner scanner = new Scanner(in);

    private  static final Logger log = LoggerFactory.getLogger(AdaptBasalInsulinDoseCmd.class);

    @Override
    @SuppressWarnings("java:S106")
    public Integer call() {

        try {
            BasalInsulin basalInsulin = new BasalInsulin();

            System.out.println("Please enter interval 1");
            var interval1 = processIntervalInput();

            System.out.println("Please enter interval 2");
            var interval2 = processIntervalInput();

            System.out.println("Please enter interval 3");
            var interval3 = processIntervalInput();

            int adaptation = basalInsulin.computeAdaptation(
                List.of(
                    interval1,
                    interval2,
                    interval3
                )
            );

            System.out.printf("Recommended adaptation for basal insulin is %d%n", adaptation);

            return 0;
        }
        catch(Exception e) {
            log.error("unexpected error while running adapt-basal-dose subcommand", e);
            return 1;
        }

    }

    private NightGlycemiaInterval processIntervalInput() {
        String intervalStr = scanner.next();

        List<Double> glycemiaList;
        try {
            glycemiaList = Arrays.stream(intervalStr.split(",")).map(String::trim).map(Double::parseDouble).toList();
        }
        catch(Exception e) {
            String message = String.format("Invalid interval input: %s", intervalStr);
            log.error(message);
            throw new IllegalArgumentException(message, e);
        }

        if (glycemiaList.size() != 2) {
            throw new IllegalArgumentException("Pas bien");
        }

        return new NightGlycemiaInterval(glycemiaList.get(0), glycemiaList.get(1));

    }

}
