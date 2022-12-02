package site.hobbyup.class_final_back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeRespDto.SubscribeListRespDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeRespDto.SubscribeSaveRespDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SubscribeService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SubscribeRepository subscribeRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Transactional
    public SubscribeSaveRespDto saveSubscribe(SubscribeSaveReqDto subscribeSaveReqDto, Long userId) {
        // user가 lesson을 구독
        // 유저확인
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저가 존재하지 않습니다.", HttpStatus.FORBIDDEN));

        // 클래스 확인
        Lesson lessonsPS = lessonRepository.findById(subscribeSaveReqDto.getLessonId()).orElseThrow(
                () -> new CustomApiException("클래스가 존재하지 않습니다.", HttpStatus.FORBIDDEN));

        try {
            Subscribe subscribe = subscribeSaveReqDto.toEntity(lessonsPS, userPS);
            SubscribeSaveRespDto subscribeSaveRespDto = new SubscribeSaveRespDto(subscribeRepository.save(subscribe));
            return subscribeSaveRespDto;
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 클래스 입니다.", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public void deleteSubscribe(Long subscribeId, Long userId) {
        // 유저확인
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("유저가 존재하지 않습니다.", HttpStatus.FORBIDDEN));

        // 해당 클래스 구독여부 확인
        Subscribe subscribePS = subscribeRepository.findById(subscribeId)
                .orElseThrow(() -> new CustomApiException("구독하지 않은 클래스입니다.", HttpStatus.FORBIDDEN));

        // 구독 취소
        subscribeRepository.deleteById(subscribePS.getId());
    }

    @Transactional
    public SubscribeListRespDto getSubscribeList(Long userId) {
        log.debug("디버그 : 구독목록보기 service");
        // 유저확인
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("유저가 존재하지 않습니다.", HttpStatus.FORBIDDEN));

        // 구독 리스트 확인
        List<Subscribe> subscribeList = subscribeRepository.findByUserId(userPS.getId());
        log.debug("디버그 : 구독리스트" + subscribeList.get(0).getLesson().getName());
        return new SubscribeListRespDto(subscribeList);
    }

}
