package site.hobbyup.class_final_back.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CustomApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
