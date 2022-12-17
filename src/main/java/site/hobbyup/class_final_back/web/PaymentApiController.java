package site.hobbyup.class_final_back.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.payment.PaymentReqDto.PaymentSaveReqDto;
import site.hobbyup.class_final_back.dto.payment.PaymentRespDto.PaymentForSellingRespDto;
import site.hobbyup.class_final_back.dto.payment.PaymentRespDto.PaymentListRespDto;
import site.hobbyup.class_final_back.dto.payment.PaymentRespDto.PaymentSaveRespDto;
import site.hobbyup.class_final_back.service.PaymentService;

@RequiredArgsConstructor
@RestController
public class PaymentApiController {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final IamportClient iamportClient = new IamportClient("1318833130671340",
      "JrgzUN4r7a5fJOCz48LmnTSv30olkOQ6kLVaToiGoHxvfI82OAjjwjlOHRYmsCnOf7bZFWRJLe21tsw4");
  private final PaymentService paymentService;

  // flutter에서 결제페이지에 요청한 데이터가 아임포트 서버에 입력된 데이터와 일치 하는지 검증하는 컨트롤러
  @PostMapping("/verifyIamport/{imp_uid}")
  public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid)
      throws IamportResponseException, IOException {
    log.info("paymentByImpUid 진입");
    return iamportClient.paymentByImpUid(imp_uid);
  }

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
    PaymentListRespDto paymentListRespDto = paymentService.getUserPaymentList(userId);
    return new ResponseEntity<>(new ResponseDto<>("결제 내역 불러오기 성공", paymentListRespDto), HttpStatus.OK);
  }

  @GetMapping("/api/expert/{userId}/mypage/selling")
  public ResponseEntity<?> getExpertSellingList(@PathVariable Long userId,
      @AuthenticationPrincipal LoginUser loginUser) {
    if (userId != loginUser.getUser().getId()) {
      throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    PaymentForSellingRespDto paymentForSellingRespDto = paymentService.getExpertSellingList(userId);
    return new ResponseEntity<>(new ResponseDto<>("판매 내역 불러오기 성공", paymentForSellingRespDto), HttpStatus.OK);
  }

  @PostMapping("/payments/cancel")
  public ResponseEntity<?> cancelPayments() {

    return new ResponseEntity<>(new ResponseDto<>("결제 완료", null), HttpStatus.CREATED);
  }

}
