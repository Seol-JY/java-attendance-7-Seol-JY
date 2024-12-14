package attendance.constant;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    크리스마스(LocalDate.of(2024, 12, 25));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static Boolean isHoliday(LocalDate date) {
        return Arrays.stream(values())
                .anyMatch(current -> current.date.equals(date));
    }
}
