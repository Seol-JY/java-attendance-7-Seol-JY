package attendance.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {
    public static final String GET_CREW_NICKNAME = "닉네임을 입력해 주세요.";
    public static final String GET_CREW_NICKNAME_FOR_EDIT = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    public static final String GET_CREW_TIME = "등교 시간을 입력해 주세요.";
    public static final String GET_DATE_FOR_EDIT = "수정하려는 날짜(일)를 입력해 주세요.";
    public static final String GET_TIME_FOR_EDIT = "수정하려는 날짜(일)를 입력해 주세요.";

    public String getCrewNickname() {
        return readLineWithPrompt(GET_CREW_NICKNAME);
    }

    public String getCrewNicknameForEdit() {
        return readLineWithPrompt(GET_CREW_NICKNAME_FOR_EDIT);
    }

    public String getCrewTime() {
        return readLineWithPrompt(GET_CREW_TIME);
    }

    public String getDateForEdit() {
        return readLineWithPrompt(GET_DATE_FOR_EDIT);
    }

    public String getTimeForEdit() {
        return readLineWithPrompt(GET_TIME_FOR_EDIT);
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
