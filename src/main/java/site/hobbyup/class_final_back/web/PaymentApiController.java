package site.hobbyup.class_final_back.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.payment.PaymentReqDto.PaymentSaveReqDto;
import site.hobbyup.class_final_back.dto.payment.PaymentRespDto.PaymentSaveRespDto;
import site.hobbyup.class_final_back.service.PaymentService;

@RequiredArgsConstructor
@RestController
public class PaymentApiController {

  private final PaymentService paymentService;

  @PostMapping("/api/lesson/{lessonId}/payment")
  public ResponseEntity<?> savePayment(@RequestBody PaymentSaveReqDto paymentSaveReqDto,
      @AuthenticationPrincipal LoginUser loginUser, @PathVariable Long lessonId) {
    PaymentSaveRespDto paymentSaveRespDto = paymentService.savePayment(paymentSaveReqDto, loginUser.getUser().getId(),
        lessonId);
    return new ResponseEntity<>(new ResponseDto<>("결제 완료", paymentSaveRespDto), HttpStatus.CREATED);
  }

  @GetMapping("/api/user/{userId}/mypage/payment")
  public ResponseEntity<?> getUserPaymentList(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
    if (userId != loginUser.getUser().getId()) {
      throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    paymentService.getUserPaymentList(userId);
    return new ResponseEntity<>(new ResponseDto<>("결제 내역 불러오기 완료", null), HttpStatus.OK);
  }

}
