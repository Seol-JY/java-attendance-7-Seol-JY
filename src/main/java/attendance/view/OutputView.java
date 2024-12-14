package attendance.view;

import attendance.constant.AttendanceStatus;
import attendance.dto.AttendanceEditInfo;
import attendance.dto.AttendanceInfo;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {
    public static final String WELCOME_MESSAGE = "\n오늘은 %d월 %d일 금요일입니다. 기능을 선택해 주세요.\n";
    public static final String OPTIONS_MESSAGE =
            """
                    1. 출석 확인
                    2. 출석 수정
                    3. 크루별 출석 기록 확인
                    4. 제적 위험자 확인
                    Q. 종료
                    """;
    public static final String ATTENDANCE_INFO_DATE = "%02d월 %02d일 %s요일";
    public static final String ATTENDANCE_INFO_TIME = " %02d:%02d (%s)";
    public static final String ATTENDANCE_INFO_TIME_ABSENT = " --:-- (결석)";
    public static final String ARROW = " -> ";
    public static final String EDIT_COMPLETED = " 수정 완료!";
    public static final String NICKNAME_RECORD = "이번 달 %s의 출석 기록입니다.\n\n";

    public void printOptions(Integer month, Integer date) {
        System.out.printf(WELCOME_MESSAGE + OPTIONS_MESSAGE, month, date);
    }

    public void printAttendanceInfo(AttendanceInfo attendanceInfo) {
        printAttendanceInfoDetail(attendanceInfo);
        System.out.println();
    }

    public void printAttendanceEditInfo(AttendanceEditInfo attendanceEditInfo) {
        printAttendanceInfoDetail(attendanceEditInfo.previous());
        System.out.print(ARROW);

        System.out.printf(ATTENDANCE_INFO_TIME, attendanceEditInfo.time().getHour(),
                attendanceEditInfo.time().getMinute(),
                attendanceEditInfo.status());
        System.out.println(EDIT_COMPLETED);
    }

    public void printAllRecord(String nickname, List<AttendanceInfo> attendanceInfos) {
        System.out.printf(NICKNAME_RECORD, nickname);
        for (AttendanceInfo info : attendanceInfos) {
            printAttendanceInfoDetail(info);
            System.out.println();
        }

        Map<AttendanceStatus, List<AttendanceInfo>> statuss = attendanceInfos.stream()
                .collect(Collectors.groupingBy(AttendanceInfo::status));
        if (statuss.get(AttendanceStatus.출석) != null) {
            System.out.println("\n출석: " + statuss.get(AttendanceStatus.출석).size() + "회");
        } else {
            System.out.println("\n출석: " + "0회");
        }
        if (statuss.get(AttendanceStatus.지각) != null) {
            System.out.println("지각: " + statuss.get(AttendanceStatus.지각).size() + "회");
        } else {
            System.out.println("지각: " + "0회");
        }
        if (statuss.get(AttendanceStatus.결석) != null) {
            System.out.println("결석: " + statuss.get(AttendanceStatus.결석).size() + "회");
        } else {
            System.out.println("결석: " + "0회");
        }

        int absent = statuss.get(AttendanceStatus.지각) != null ? statuss.get(AttendanceStatus.지각).size() / 3 : 0;
        int absent2 = statuss.get(AttendanceStatus.결석) != null ? statuss.get(AttendanceStatus.결석).size() : 0;

        if (absent + absent2 >= 2) {
            System.out.println("\n경고 대상자입니다.");
        } else if (absent + absent2 >= 3) {
            System.out.println("\n면담 대상자입니다.");
        } else if (absent + absent2 >= 5) {
            System.out.println("\n제적 대상자입니다.");
        }
    }

    private void printAttendanceInfoDetail(AttendanceInfo attendanceInfo) {
        System.out.printf(ATTENDANCE_INFO_DATE,
                attendanceInfo.date().getMonthValue(),
                attendanceInfo.date().getDayOfMonth(),
                attendanceInfo.date().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN)
        );
        if (attendanceInfo.time() != null) {
            System.out.printf(ATTENDANCE_INFO_TIME, attendanceInfo.time().getHour(),
                    attendanceInfo.time().getMinute(),
                    attendanceInfo.status());
            return;
        }

        System.out.print(ATTENDANCE_INFO_TIME_ABSENT);
    }
}
