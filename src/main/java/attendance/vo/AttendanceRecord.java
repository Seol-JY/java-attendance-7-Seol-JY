package attendance.vo;

import static attendance.constant.ExceptionMessage.CANNOT_ATTENDANCE_OUTSIDE_OPERATING;
import static attendance.constant.ExceptionMessage.INVALID_FORMAT_INPUT;
import static attendance.constant.ExceptionMessage.NOT_CAMPUS_DAY;

import attendance.constant.Holiday;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceRecord {
    private static LocalTime OPERATIING_START_TIME = LocalTime.of(8, 0);
    private static LocalTime OPERATIING_END_TIME = LocalTime.of(23, 0);

    private final LocalDate date;
    private final LocalTime time;

    private AttendanceRecord(LocalDate date, LocalTime time) {
        validateCampusDay(date);
        validateOperatingTime(time);

        this.date = date;
        this.time = time;
    }

    public static AttendanceRecord of(Integer date, LocalTime time) {
        try {
            return new AttendanceRecord(LocalDate.of(2024, 12, date), time);
        } catch (DateTimeException ex) {
            throw new IllegalArgumentException(INVALID_FORMAT_INPUT.message());
        }
    }

    public static AttendanceRecord of(LocalDate date, LocalTime time) {
        try {
            return new AttendanceRecord(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()), time);
        } catch (DateTimeException ex) {
            throw new IllegalArgumentException(INVALID_FORMAT_INPUT.message());
        }
    }

    private void validateCampusDay(LocalDate date) {
        if (Holiday.isHoliday(date) || isWeekend(date)) {
            throw new IllegalArgumentException(
                    NOT_CAMPUS_DAY.format(
                            date.getMonthValue(),
                            date.getDayOfMonth(),
                            date.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN)
                    ));
        }
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private void validateOperatingTime(LocalTime time) {
        if (time.isBefore(OPERATIING_START_TIME) || time.isAfter(OPERATIING_END_TIME)) {
            throw new IllegalArgumentException(CANNOT_ATTENDANCE_OUTSIDE_OPERATING.message());
        }
    }

    public LocalDate getDate() {
        return date;
    }
}
