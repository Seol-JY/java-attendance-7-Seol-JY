package attendance.dto;

import attendance.constant.AttendanceStatus;
import java.time.LocalTime;

public record AttendanceEditInfo(
        AttendanceInfo previous,
        LocalTime time,
        AttendanceStatus status
) {
    public static AttendanceEditInfo of(
            AttendanceInfo previous,
            LocalTime time,
            AttendanceStatus status
    ) {
        return new AttendanceEditInfo(previous, time, status);
    }
}

