package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeRespDto.SubscribeDeleteRespDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeRespDto.SubscribeSaveRespDto;
import site.hobbyup.class_final_back.service.SubscribeService;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe")
    public ResponseEntity<?> saveSubscribe(@RequestBody SubscribeSaveReqDto subscribeSaveReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : 구독 controller시작");
        SubscribeSaveRespDto SubscribeSaveRespDto = subscribeService.saveSubscribe(subscribeSaveReqDto,
                loginUser.getUser().getId());

        return new ResponseEntity<>(new ResponseDto<>("구독 완료", SubscribeSaveRespDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/lesson/{lessonId}/subscribe")
    public ResponseEntity<?> deleteSubscribe(@PathVariable Long lessonId,
            @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : 구독취소 controller시작");
        SubscribeDeleteRespDto subscribeDeleteRespDto = subscribeService.deleteSubscribe(lessonId,
                loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("구독 취소", subscribeDeleteRespDto), HttpStatus.CREATED);
    }

}