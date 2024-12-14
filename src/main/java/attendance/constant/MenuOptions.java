package attendance.constant;

import static attendance.constant.ExceptionMessage.INVALID_FORMAT_INPUT;

import java.util.Arrays;

public enum MenuOptions {
    CREATE("1"),
    EDIT("2"),
    GET("3"),
    DISMISSAL("4"),
    QUIT("Q"),
    ;
    private final String number;

    MenuOptions(String number) {
        this.number = number;
    }

    public static MenuOptions getMenuOptionsByInput(String input) {
        return Arrays.stream(values())
                .filter(menu -> menu.number.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_FORMAT_INPUT.message()));
    }
}
