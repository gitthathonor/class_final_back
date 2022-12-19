package site.hobbyup.class_final_back.oauth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import site.hobbyup.class_final_back.oauth.dto.KakaoProfile;
import site.hobbyup.class_final_back.oauth.dto.OAuthToken;

@Service
public class KakaoService {

    public OAuthToken tokenRequest(String code) {

        System.out.println("디버그:토큰요청");

        // REST API 요청하는 클래스
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "ff4464b0234d6ac15b780cbac3a127cb");
        body.add("redirect_uri", "http://localhost:8000/oauth/kakao");
        body.add("code", code);

        // HttpHeader와 HttpBody 담기기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers); // params : body

        OAuthToken oAuthToken = restTemplate
                .exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, OAuthToken.class)
                .getBody();
        return oAuthToken;
    }

    public KakaoProfile userInfoRequest(OAuthToken oAuthToken) {

        System.out.println("디버그:정보요청");

        // REST API 요청하는 클래스
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody 담기기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        KakaoProfile kakaoProfile = restTemplate
                .exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, KakaoProfile.class)
                .getBody();

        return kakaoProfile;
    }
}