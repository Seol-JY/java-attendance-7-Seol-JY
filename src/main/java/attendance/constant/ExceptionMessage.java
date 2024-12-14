package attendance.constant;

public enum ExceptionMessage {
    INVALID_FORMAT_INPUT("잘못된 형식을 입력하였습니다."),
    NOT_FOUND_NICKNAME("등록되지 않은 닉네임입니다."),
    NOT_CAMPUS_DAY("%d월 %d일 %s요일은 등교일이 아닙니다."),
    CANNOT_EDIT_FUTURE("아직 수정할 수 없습니다."),
    CANNOT_ATTENDANCE_OUTSIDE_OPERATING("캠퍼스 운영 시간에만 출석이 가능합니다."),
    ALREADY_ATTENDANCE("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return "[ERROR] " + message;
    }

    public String format(Object... args) {
        return "[ERROR] " + String.format(message, args);
    }
}
