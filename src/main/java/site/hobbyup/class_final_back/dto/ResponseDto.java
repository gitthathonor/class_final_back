package site.hobbyup.class_final_back.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {
    private final String msg;
    private final T data;
}
