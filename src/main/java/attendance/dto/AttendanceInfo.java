package attendance.dto;

import attendance.constant.AttendanceStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceInfo(
        LocalDate date,
        LocalTime time,
        DayOfWeek dayOfWeek,
        AttendanceStatus status
) {
    public static AttendanceInfo of(LocalDate date, LocalTime time, AttendanceStatus status) {
        return new AttendanceInfo(date, time, date.getDayOfWeek(), status);
    }
}

