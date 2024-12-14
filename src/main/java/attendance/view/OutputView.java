package attendance.view;

import attendance.constant.AttendanceStatus;
import attendance.dto.AttendanceInfo;
import java.time.format.TextStyle;
import java.util.Locale;

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
    public static final String ATTENDANCE_INFO_DATE = "%02d월 %02d일 %s요일";
    public static final String ATTENDANCE_INFO_TIME = " %02d:%02d (%s)\n";
    public static final String ATTENDANCE_INFO_TIME_ABSENT = " --:-- (결석)\n";

    public void printOptions(Integer month, Integer date) {
        System.out.printf(WELCOME_MESSAGE + OPTIONS_MESSAGE, month, date);
    }

    public void printAttendanceInfo(AttendanceInfo attendanceInfo) {
        System.out.printf(ATTENDANCE_INFO_DATE,
                attendanceInfo.date().getMonthValue(),
                attendanceInfo.date().getDayOfMonth(),
                attendanceInfo.date().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN)
        );
        if (!attendanceInfo.status().equals(AttendanceStatus.결석)) {
            System.out.printf(ATTENDANCE_INFO_TIME, attendanceInfo.time().getHour(),
                    attendanceInfo.time().getMinute(),
                    attendanceInfo.status());
            return;
        }

        System.out.print(ATTENDANCE_INFO_TIME_ABSENT);
    }
}
