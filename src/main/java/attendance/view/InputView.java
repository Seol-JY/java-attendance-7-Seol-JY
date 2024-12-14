package attendance.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {

    private String readLineWithPrompt(String prompt) {
        System.out.println(prompt);
        return readLine().strip();
    }

    private String readLineWithFormattedPrompt(final String prompt, final Object... args) {
        System.out.printf(prompt, args);
        return readLine().strip();
    }
}
