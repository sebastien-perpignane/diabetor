package sebastien.perpignane.diabetor.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "diabetor",
    subcommands = {
        PunctualQuickInsulinCmd.class,
        AdaptBaseQuickInsulinDoseCmd.class,
        AdaptBasalInsulinDoseCmd.class
    }
)
public class CliMain implements Runnable {

    @SuppressWarnings("java:S106")
    @Override
    public void run() {
        System.out.println("Please use subcommand");
    }

    public static void main(String[] args) {

        int result = new CommandLine(new CliMain()).execute(args);

        System.exit(result);

    }

}
