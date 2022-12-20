package site.hobbyup.class_final_back.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserEnum {
    USER("일반회원"), EXPERT("전문가"), ADMIN("관리자");

    private String value;
}
