package attendance.domain;

import static attendance.constant.ExceptionMessage.ALREADY_ATTENDANCE;

import attendance.vo.AttendanceRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Attendance {
    private final String nickname;
    private final Map<LocalDate, AttendanceRecord> records;

    private Attendance(String nickname, Map<LocalDate, AttendanceRecord> records) {
        this.nickname = nickname;
        this.records = records;
    }

    public static Attendance of(String nickname, List<AttendanceRecord> recordsForInitialize) {
        Map<LocalDate, AttendanceRecord> records = recordsForInitialize.stream()
                .collect(Collectors.toMap(
                                AttendanceRecord::getDate,
                                Function.identity(),
                                (existing, replacement) -> {
                                    throw new IllegalArgumentException(ALREADY_ATTENDANCE.message());
                                }
                        )
                );

        return new Attendance(nickname, records);
    }

    public void attend(LocalDate date, LocalTime time) {
        AttendanceRecord record = AttendanceRecord.of(date, time);
        if (records.putIfAbsent(date, record) != null) {
            throw new IllegalArgumentException(ALREADY_ATTENDANCE.message());
        }
    }
}
