package attendance.domain;

import static attendance.constant.ExceptionMessage.NOT_FOUND_NICKNAME;

import attendance.constant.AttendanceStatus;
import attendance.constant.Holiday;
import attendance.dto.AttendanceEditInfo;
import attendance.dto.AttendanceInfo;
import attendance.dto.CrewFileDto;
import attendance.vo.AttendanceRecord;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public AttendanceEditInfo edit(String nickname, LocalDate dateToEdit, LocalTime timeForEdit) {
        Attendance attendance = records.get(nickname);
        AttendanceInfo previousInfo = attendance.edit(dateToEdit, timeForEdit);

        return AttendanceEditInfo.of(previousInfo, timeForEdit, Judge.getAttendanceStatus(dateToEdit, timeForEdit));
    }

    public List<AttendanceInfo> getAll(String nickname) {
        Attendance attendance = records.get(nickname);
        List<AttendanceInfo> list = new ArrayList<>();
        LocalDateTime datetime = DateTimes.now();

        for (int dateNumber = 1; dateNumber < datetime.getDayOfMonth(); dateNumber++) {
            LocalDate date = LocalDate.of(2024, 12, dateNumber);
            if (!isCampusDay(date)) {
                continue;
            }
            
            AttendanceRecord record = attendance.getByDate(date);

            if (record == null) {
                list.add(AttendanceInfo.of(date, null, AttendanceStatus.결석));
                continue;
            }
            list.add(AttendanceInfo.of(date, record.getTime(), Judge.getAttendanceStatus(date, record.getTime())));
        }

        return list;
    }

    private boolean isCampusDay(LocalDate date) {
        if (Holiday.isHoliday(date) || isWeekend(date)) {
            return false;
        }

        return true;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}
