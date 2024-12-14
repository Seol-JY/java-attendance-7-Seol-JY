package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CrewFileDto(
        String nickname,
        LocalDate date,
        LocalTime time
) implements FileDto {
    public static CrewFileDto of(String nickname, String datetime) {
        String[] rawDatetime = datetime.split(" ");
        return new CrewFileDto(nickname, LocalDate.parse(rawDatetime[0]), LocalTime.parse(rawDatetime[1]));
    }
}
