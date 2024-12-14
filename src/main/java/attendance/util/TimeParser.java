package attendance.util;

import static attendance.constant.ExceptionMessage.INVALID_FORMAT_INPUT;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TimeParser {
    public static LocalTime parse(final String input) {
        validateNull(input);
        String trimmedInput = input.strip();
        validateEmpty(trimmedInput);

        return parseToDateTime(trimmedInput);
    }

    private static void validateNull(final String input) {
        if (input == null) {
            throw new IllegalArgumentException(INVALID_FORMAT_INPUT.message());
        }
    }

    private static void validateEmpty(final String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException(INVALID_FORMAT_INPUT.message());
        }
    }

    private static LocalTime parseToDateTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(INVALID_FORMAT_INPUT.message());
        }
    }
}
