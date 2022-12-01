package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;
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

        SubscribeSaveRespDto SubscribeSaveRespDto = subscribeService.saveSubscribe(subscribeSaveReqDto,
                loginUser.getUser().getId());

        return new ResponseEntity<>(new ResponseDto<>("구독 완료", SubscribeSaveRespDto), HttpStatus.CREATED);
    }

}