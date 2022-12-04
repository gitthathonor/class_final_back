package site.hobbyup.class_final_back.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DayEnum {
    MONDAY("월요일"), TUESDAY("화요일"), WEDNESDAY("수요일"),
    THURSDAY("목요일"), FRIDAY("금요일"), SATURDAY("토요일"), SUNDAY("일요일");

    private String value;
}
