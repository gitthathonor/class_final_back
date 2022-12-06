package site.hobbyup.class_final_back.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.payment.PaymentRepository;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.service.PaymentService;

@RequiredArgsConstructor
@RestController
public class PaymentApiController {

  private final PaymentService paymentService;

  @PostMapping("/api/lesson/{lessonId}/payment")
  public ResponseEntity<?> savePayment() {
    paymentService.savePayment();
    return new ResponseEntity<>(new ResponseDto<>("결제 완료", null), HttpStatus.CREATED);
  }

}
