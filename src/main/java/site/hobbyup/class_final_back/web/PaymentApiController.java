package site.hobbyup.class_final_back.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import site.hobbyup.class_final_back.dto.ResponseDto;

@RestController
public class PaymentApiController {
  @PostMapping("/api/lesson/{lessonId}/payment")
  public ResponseEntity<?> payForLesson(String param) {
    return new ResponseEntity<>(new ResponseDto<>(param, null), HttpStatus.CREATED);
  }

}
