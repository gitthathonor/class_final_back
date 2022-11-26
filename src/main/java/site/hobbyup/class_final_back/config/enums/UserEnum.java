package site.hobbyup.class_final_back.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserEnum {
    USER("수강생"), MASTER("전문가");

    private String value;
}
