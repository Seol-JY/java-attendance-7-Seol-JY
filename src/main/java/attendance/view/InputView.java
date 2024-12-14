package attendance.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {
    public static final String GET_CREW_NICKNAME = "닉네임을 입력해 주세요.\n";
    public static final String GET_CREW_TIME = "등교 시간을 입력해 주세요.\n";

    public String getCrewNickname() {
        return readLineWithPrompt(GET_CREW_NICKNAME);
    }

    public String getCrewTime() {
        return readLineWithPrompt(GET_CREW_TIME);
    }

    private String readLineWithPrompt(String prompt) {
        System.out.println(prompt);
        return readLine().strip();
    }

    private String readLineWithFormattedPrompt(final String prompt, final Object... args) {
        System.out.printf(prompt, args);
        return readLine().strip();
    }
}
