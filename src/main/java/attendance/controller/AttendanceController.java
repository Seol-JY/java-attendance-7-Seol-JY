package attendance.controller;

import attendance.domain.Attendances;
import attendance.dto.AttendanceEditInfo;
import attendance.dto.CrewFileDto;
import attendance.loader.FileDataLoader;
import attendance.util.DecemberLocalDateParser;
import attendance.util.TimeParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceController {
    public static final String ATTENDANCES_FILE_PATH = "src/main/resources/attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(
            InputView inputView,
            OutputView outputView
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<CrewFileDto> crewDtos = new FileDataLoader<>(CrewFileDto.class).load(ATTENDANCES_FILE_PATH);
        Attendances attendances = Attendances.from(crewDtos);
        // TODO: 변경 필요
//        LocalDateTime datetime = DateTimes.now();
        LocalTime t = DateTimes.now().toLocalTime();

        LocalDateTime datetime = LocalDateTime.of(LocalDate.of(2024, 12, 5), t);

        boolean shouldContinue = false;
        do {
            outputView.printOptions(datetime.getMonthValue(), datetime.getDayOfMonth());

            ///1
            // TODO: 휴일검증 필요
//            String nickname = inputView.getCrewNickname();
//            attendances.validateCrewNickname(nickname);
//
//            String rawCrewTime = inputView.getCrewTime();
//            LocalTime time = TimeParser.parse(rawCrewTime);
//
//            AttendanceInfo attendanceInfo = attendances.attend(nickname, datetime.toLocalDate(), time);
//            outputView.printAttendanceInfo(attendanceInfo);

            ///2
            // TODO: 휴일검증 필요
            String nickname2 = inputView.getCrewNicknameForEdit();
            String rawDate2 = inputView.getDateForEdit();
            LocalDate date = DecemberLocalDateParser.parse(rawDate2);

            String rawTime2 = inputView.getTimeForEdit();
            LocalTime time2 = TimeParser.parse(rawTime2);

            AttendanceEditInfo attendanceEditInfo = attendances.edit(nickname2, date, time2);
            outputView.printAttendanceEditInfo(attendanceEditInfo);

        } while (shouldContinue);
    }
}
