package site.hobbyup.class_final_back.oauth.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.oauth.dto.KakaoProfile;
import site.hobbyup.class_final_back.oauth.dto.KakaoRespDto;
import site.hobbyup.class_final_back.oauth.dto.OAuthToken;
import site.hobbyup.class_final_back.oauth.service.KakaoService;

@RequiredArgsConstructor
@Controller
public class KakaoApiController {
    private final KakaoService kakaoService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/oauth/kakao")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {

        // 토큰 요청
        OAuthToken oAuthToken = kakaoService.tokenRequest(code);
        // 받은 토큰으로 유저정보 요청
        KakaoProfile kakaoProfile = kakaoService.userInfoRequest(oAuthToken);

        String email = kakaoProfile.getKakao_account().getEmail();
        int index = email.indexOf("@");
        String username = (kakaoProfile.getKakao_account().getEmail().substring(0,
                index));

        KakaoRespDto kakaoUser = KakaoRespDto.builder()
                .username(username)
                .password(kakaoProfile.getKakao_account().getEmail() + kakaoProfile.getId())
                .email(email)
                .oauth("kakao")
                .build();

        return new ResponseEntity<>(new ResponseDto<>("카카오 유저정보", kakaoUser), HttpStatus.OK);
    }
}