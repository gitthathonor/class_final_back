package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeRespDto.SubscribeListRespDto;
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

    @DeleteMapping("/api/subscribe/{subscribeId}")
    public ResponseEntity<?> deleteSubscribe(@PathVariable Long subscribeId,
            @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : 구독취소 controller시작");
        subscribeService.deleteSubscribe(subscribeId,
                loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("구독 취소", null), HttpStatus.CREATED);
    }

    @GetMapping("api/user/{userId}/subscribe")
    public ResponseEntity<?> getSubscribeList(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : 구독목록보기 controller");
        if (userId != loginUser.getUser().getId()) { // 권한체크 - 세션의 id, userid가 같으면 상세보기
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        SubscribeListRespDto subscribeListRespDto = subscribeService.getSubscribeList(userId);
        log.debug("디버그 : " + subscribeListRespDto.getSubscribes().get(0).getLesson().getName());
        return new ResponseEntity<>(new ResponseDto<>("구독리스트보기", subscribeListRespDto), HttpStatus.OK);
    }
}