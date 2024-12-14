package attendance.controller;

import static attendance.constant.ExceptionMessage.NOT_CAMPUS_DAY;

import attendance.constant.Holiday;
import attendance.constant.MenuOptions;
import attendance.domain.Attendances;
import attendance.dto.AttendanceEditInfo;
import attendance.dto.AttendanceInfo;
import attendance.dto.CrewFileDto;
import attendance.loader.FileDataLoader;
import attendance.util.DecemberLocalDateParser;
import attendance.util.TimeParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

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
        LocalDateTime datetime = DateTimes.now();
//        LocalTime t = DateTimes.now().toLocalTime();
//        LocalDateTime datetime = LocalDateTime.of(LocalDate.of(2024, 12, 5), t);

        MenuOptions option;
        do {
            outputView.printOptions(datetime.getMonthValue(), datetime.getDayOfMonth());
            String rawOption = inputView.getMenuOption();
            option = MenuOptions.getMenuOptionsByInput(rawOption);

            if (option == MenuOptions.CREATE) {
                create(attendances, datetime);
                continue;
            }
            if (option == MenuOptions.EDIT) {
                edit(attendances);
                continue;
            }
            if (option == MenuOptions.GET) {
                get(attendances);
                continue;
            }
        } while (option != MenuOptions.QUIT);
    }

    private void create(Attendances attendances, LocalDateTime datetime) {
        validateCampusDay(datetime.toLocalDate());
        String nickname = inputView.getCrewNickname();
        attendances.validateCrewNickname(nickname);

        String rawCrewTime = inputView.getCrewTime();
        LocalTime time = TimeParser.parse(rawCrewTime);

        AttendanceInfo attendanceInfo = attendances.attend(nickname, datetime.toLocalDate(), time);
        outputView.printAttendanceInfo(attendanceInfo);
    }

    private void edit(Attendances attendances) {
        String nickname = inputView.getCrewNicknameForEdit();
        attendances.validateCrewNickname(nickname);
        String rawDate2 = inputView.getDateForEdit();
        LocalDate date = DecemberLocalDateParser.parse(rawDate2);

        String rawTime2 = inputView.getTimeForEdit();
        LocalTime time2 = TimeParser.parse(rawTime2);

        AttendanceEditInfo attendanceEditInfo = attendances.edit(nickname, date, time2);
        outputView.printAttendanceEditInfo(attendanceEditInfo);
    }

    private void get(Attendances attendances) {
        String nickname = inputView.getCrewNicknameForGet();
        attendances.validateCrewNickname(nickname);
        List<AttendanceInfo> attendanceInfos = attendances.getAll(nickname);

        outputView.printAllRecord(nickname, attendanceInfos);
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


}
