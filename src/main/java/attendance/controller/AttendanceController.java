package attendance.controller;

import attendance.domain.Attendances;
import attendance.dto.CrewFileDto;
import attendance.loader.FileDataLoader;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
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

        boolean shouldContinue = false;
        do {
            LocalDateTime datetime = DateTimes.now();
            outputView.printOptions(datetime.getMonthValue(), datetime.getDayOfMonth());
        } while (shouldContinue);
    }
}
