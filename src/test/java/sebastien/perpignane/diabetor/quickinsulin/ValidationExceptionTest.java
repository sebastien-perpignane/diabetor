package sebastien.perpignane.diabetor.quickinsulin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationExceptionTest {

    @Test
    void testFormattedMessages_defaultDelimiter() {

        String message1 = "message 1";
        String message2 = "message 2";

        ValidationException exception = new ValidationException(
            List.of(
                message1,
                message2
            )
        );

        assertThat(exception.formattedMessages())
                .isEqualTo("message 1" + System.lineSeparator() + "message 2");

    }

    @Test
    void testFormattedMessages_customDelimiter() {

        String message1 = "message 1";
        String message2 = "message 2";

        ValidationException exception = new ValidationException(
                List.of(
                        message1,
                        message2
                )
        );

        assertThat(exception.formattedMessages("\t"))
                .isEqualTo("message 1\tmessage 2");

    }
}
