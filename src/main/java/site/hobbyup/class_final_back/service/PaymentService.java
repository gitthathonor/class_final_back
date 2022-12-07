package site.hobbyup.class_final_back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.coupon.Coupon;
import site.hobbyup.class_final_back.domain.coupon.CouponRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.payment.Payment;
import site.hobbyup.class_final_back.domain.payment.PaymentRepository;
import site.hobbyup.class_final_back.domain.paymentType.PaymentType;
import site.hobbyup.class_final_back.domain.paymentType.PaymentTypeRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.payment.PaymentReqDto.PaymentSaveReqDto;
import site.hobbyup.class_final_back.dto.payment.PaymentRespDto.PaymentSaveRespDto;

@RequiredArgsConstructor
@Service
public class PaymentService {

  private final UserRepository userRepository;
  private final PaymentRepository paymentRepository;
  private final LessonRepository lessonRepository;
  private final SubscribeRepository subscribeRepository;
  private final PaymentTypeRepository paymentTypeRepository;
  private final CouponRepository couponRepository;

  @Transactional
  public PaymentSaveRespDto savePayment(PaymentSaveReqDto paymentSaveReqDto, Long userId, Long lessonId) {

    // 1. 현재 유저가 이 클래스를 찜하고 있는지 없는지 확인
    Optional<Subscribe> subscribeOP = subscribeRepository.findByUserIdAndLessonId(userId, lessonId);

    // 2. 유저가 찜을 하고 있다면 찜한 목록에서 제거하기
    if (subscribeOP.isPresent()) {
      Subscribe subscribePS = subscribeOP.get();
      subscribeRepository.delete(subscribePS);
    }

    // 3. 결제정보 등록하기
    User userPS = userRepository.findById(userId)
        .orElseThrow(() -> new CustomApiException("가입된 유저가 없습니다.", HttpStatus.BAD_REQUEST));
    Lesson lessonPS = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new CustomApiException("개설된 레슨이 없습니다.", HttpStatus.BAD_REQUEST));
    List<Coupon> couponListPS = couponRepository.findAllByUserId(userId);
    Coupon couponPS = couponListPS.get(0);
    PaymentType paymentTypePS = paymentTypeRepository.findById(paymentSaveReqDto.getPaymentTypeId())
        .orElseThrow(() -> new CustomApiException("없는 결제 방식입니다..", HttpStatus.BAD_REQUEST));
    Payment payment = paymentSaveReqDto.toEntity(userPS, couponPS, lessonPS, paymentTypePS);
    Payment paymentPS = paymentRepository.save(payment);
    return new PaymentSaveRespDto(paymentPS);
  }

  // 결제 취소하기

}