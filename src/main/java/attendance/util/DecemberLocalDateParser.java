package attendance.util;

import static attendance.constant.ExceptionMessage.INVALID_FORMAT_INPUT;

import java.time.LocalDate;

public class DecemberLocalDateParser {
    private static final int MAX_DIGITS_TO_PREVENT_OVERFLOW = 3;

    public static LocalDate parse(final String input) {
        validateNull(input);

        String trimmedInput = input.strip();
        validateEmpty(trimmedInput);
        validateOverflow(trimmedInput);

        return LocalDate.of(2024, 12, parseToInt(trimmedInput));
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

    private static void validateOverflow(final String input) {
        if (input.length() >= MAX_DIGITS_TO_PREVENT_OVERFLOW) {
            throw new IllegalArgumentException(INVALID_FORMAT_INPUT.message());
        }
    }

    private static Integer parseToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_FORMAT_INPUT.message());
        }
    }
}
