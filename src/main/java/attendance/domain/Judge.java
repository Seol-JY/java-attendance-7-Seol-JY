package attendance.domain;

import attendance.constant.AttendanceStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Judge {
    private static LocalTime MONDAY_TRAINING_START_TIME = LocalTime.of(13, 0);
    private static LocalTime DEFAULT_TRAINING_START_TIME = LocalTime.of(10, 0);

    public static AttendanceStatus getAttendanceStatus(LocalDate date, LocalTime time) {
        if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            if (MONDAY_TRAINING_START_TIME.plusMinutes(6).isBefore(time)) {
                return AttendanceStatus.출석;
            }
            if (MONDAY_TRAINING_START_TIME.plusMinutes(31).isAfter(time)) {
                return AttendanceStatus.지각;
            }
            return AttendanceStatus.결석;
        }

        if (DEFAULT_TRAINING_START_TIME.plusMinutes(6).isAfter(time)) {
            return AttendanceStatus.출석;
        }
        if (DEFAULT_TRAINING_START_TIME.plusMinutes(31).isAfter(time)) {
            return AttendanceStatus.지각;
        }
        return AttendanceStatus.결석;
    }
}
