package attendance.domain;

import attendance.dto.CrewFileDto;
import attendance.vo.AttendanceRecord;
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
}
