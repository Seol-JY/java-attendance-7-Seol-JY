package attendance.domain;

import static attendance.constant.ExceptionMessage.NOT_FOUND_NICKNAME;

import attendance.constant.AttendanceStatus;
import attendance.dto.AttendanceInfo;
import attendance.dto.CrewFileDto;
import attendance.vo.AttendanceRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Attendances {
    private final Map<String, Attendance> records;

    private Attendances(Map<String, Attendance> records) {
        this.records = records;
    }

    public static Attendances from(List<CrewFileDto> dtos) {
        Map<String, List<CrewFileDto>> collects = dtos.stream().collect(
                Collectors.groupingBy(CrewFileDto::nickname)
        );

        Map<String, Attendance> records = new HashMap<>();
        for (String nickname : collects.keySet()) {
            List<AttendanceRecord> attendanceRecords = collects.get(nickname).stream().map(
                    dto -> AttendanceRecord.of(dto.date(), dto.time())
            ).toList();

            records.put(nickname, Attendance.of(nickname, attendanceRecords));
        }

        return new Attendances(records);
    }

    public void validateCrewNickname(String nickname) {
        if (records.keySet().stream().anyMatch(current -> current.equals(nickname))) {
            return;
        }

        throw new IllegalArgumentException(NOT_FOUND_NICKNAME.message());
    }

    public AttendanceInfo attend(String nickname, LocalDate date, LocalTime time) {
        Attendance attendance = records.get(nickname);
        attendance.attend(date, time);
        AttendanceStatus status = Judge.getAttendanceStatus(date, time);
        return AttendanceInfo.of(date, time, status);
    }
}
