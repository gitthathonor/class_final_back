package site.hobbyup.class_final_back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import site.hobbyup.class_final_back.dto.payment.PaymentRespDto.PaymentForSellingRespDto;
import site.hobbyup.class_final_back.dto.payment.PaymentRespDto.PaymentListRespDto;
import site.hobbyup.class_final_back.dto.payment.PaymentRespDto.PaymentSaveRespDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentService {

  private final Logger log = LoggerFactory.getLogger(getClass());

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
      subscribeRepository.deleteById(subscribePS.getId());
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

    // 4. 사용한 쿠폰 삭제
    couponRepository.deleteById(couponPS.getId());

    return new PaymentSaveRespDto(paymentPS);
  }

  // 결제 내역 보기
  public PaymentListRespDto getUserPaymentList(Long userId) {
    log.debug("디버그 : PaymentService-getUserPaymentList 실행");
    // 결제 내역 정보들 들고오기
    List<Payment> paymentListPS = paymentRepository.findAllByUserId(userId);
    log.debug("디버그 : paymentListPS의 사이즈 : " + paymentListPS.size());

    return new PaymentListRespDto(paymentListPS);

  }

  // 판매 내역 보기
  public PaymentForSellingRespDto getExpertSellingList(Long userId) {
    // 1. 현재 전문가의 id로 등록되어 있는 모든 레슨을 찾음
    List<Lesson> lessonListPS = lessonRepository.findByUserId(userId);
    List<Long> lessonIdList = new ArrayList<>();

    for (int i = 0; i < lessonListPS.size(); i++) {
      lessonIdList.add(lessonListPS.get(i).getId());
    }

    // 2. 그 레슨들을 다시 결제 내역에서 찾음
    List<Payment> paymentSellingListPS = paymentRepository.findAllById(lessonIdList);

    return new PaymentForSellingRespDto(paymentSellingListPS);
  }

  // 결제 취소하기

}
