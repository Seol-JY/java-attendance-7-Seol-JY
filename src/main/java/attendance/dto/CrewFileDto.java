package attendance.dto;

public record CrewFileDto(
        String nickname,
        String datetime
) implements FileDto {
    public static CrewFileDto of(String nickname, String datetime) {
        return new CrewFileDto(nickname, datetime);
    }
}
