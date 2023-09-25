package sebastien.perpignane.diabetor.quickinsulin;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {

    private final List<String> validationMessages = new ArrayList<>();

    public ValidationException(List<String> validationMessages) {
        super(String.join(System.lineSeparator(), validationMessages));
        this.validationMessages.addAll(validationMessages);
    }

    public String formattedMessages(String delimiter) {
        return String.join(delimiter, validationMessages);
    }

    public String formattedMessages() {
        return formattedMessages(System.lineSeparator());
    }

}
