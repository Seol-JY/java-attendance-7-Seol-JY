package attendance.view;

public class OutputView {
    public static final String WELCOME_MESSAGE = "오늘은 %d월 %d일 금요일입니다. 기능을 선택해 주세요.\n";
    public static final String OPTIONS_MESSAGE =
            """
                    1. 출석 확인
                    2. 출석 수정
                    3. 크루별 출석 기록 확인
                    4. 제적 위험자 확인
                    Q. 종료
                    """;

    public void printOptions(Integer month, Integer date) {
        System.out.printf(WELCOME_MESSAGE + OPTIONS_MESSAGE, month, date);
    }
}
