package site.hobbyup.class_final_back.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.payment.PaymentRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.payment.PaymentReqDto.PaymentSaveReqDto;

@RequiredArgsConstructor
@Service
public class PaymentService {

  private final UserRepository userRepository;
  private final PaymentRepository paymentRepository;
  private final LessonRepository lessonRepository;
  private final SubscribeRepository subscribeRepository;

  public void savePayment(PaymentSaveReqDto paymentSaveReqDto, Long userId, Long lessonId) {

    // 1. 현재 유저가 이 클래스를 찜하고 있는지 없는지 확인
    List<Subscribe> subscribeListPS = subscribeRepository.findAllByLessonId(userId, lessonId);

    // 2. 유저가 찜을 하고 있다면 찜한 목록에서 제거하기
    if (subscribeListPS.size() == 0) {

    }

    // 3. 결제정보 등록하기
  }

  // 결제 취소하기

}
