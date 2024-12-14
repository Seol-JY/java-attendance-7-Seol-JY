package attendance.controller;

import attendance.dto.CrewFileDto;
import attendance.loader.FileDataLoader;
import attendance.view.InputView;
import attendance.view.OutputView;
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
        List<CrewFileDto> products = new FileDataLoader<>(CrewFileDto.class).load(ATTENDANCES_FILE_PATH);

    }
}
