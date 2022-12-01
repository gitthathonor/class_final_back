package site.hobbyup.class_final_back.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeReqDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeRespDto.SubscribeSaveRespDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SubscribeService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SubscribeRepository subscribeRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public SubscribeSaveRespDto saveSubscribe(SubscribeSaveReqDto subscribeSaveReqDto, Long userId) {
        // user가 lesson을 구독
        // 유저확인
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("유저가 존재하지 않습니다."));
        // 클래스 확인
        Lesson lessonsPS = lessonRepository.findById(subscribeSaveReqDto.getLesson().getId()).orElseThrow(
                () -> new RuntimeException("클래스가 존재하지 않습니다."));
        Subscribe subscribePS = subscribeSaveReqDto.toEntity(userPS);
        return new SubscribeSaveRespDto(subscribeRepository.save(subscribePS));
    }

}
