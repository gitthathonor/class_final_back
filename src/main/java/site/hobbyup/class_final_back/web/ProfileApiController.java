package site.hobbyup.class_final_back.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileSaveReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileUpdateReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileDetailRespDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileSaveRespDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileUpdateRespDto;
import site.hobbyup.class_final_back.service.ProfileService;

@RequiredArgsConstructor
@RestController
public class ProfileApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProfileService profileService;

    @PostMapping("/api/profile")
    public ResponseEntity<?> saveProfile(@RequestBody ProfileSaveReqDto profileSaveReqDto,
            @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        log.debug("디버그 : controller - 프로필 등록 시작");
        ProfileSaveRespDto profileSaveRespDto = profileService.saveProfile(profileSaveReqDto,
                loginUser.getUser().getId());
        log.debug("디버그 : controller - 프로필 등록 끝");
        return new ResponseEntity<>(new ResponseDto<>("프로필 등록", profileSaveRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/user/{userId}/profile")
    public ResponseDto<?> detailProfile(@PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : controller - 프로필 상세보기 시작");
        if (userId != loginUser.getUser().getId()) { // 권한체크 - 세션의 id, userid가 같으면 상세보기
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        ProfileDetailRespDto profileDetailRespDto = profileService.detailProfile(userId);
        log.debug("디버그 : controller - 프로필 상세보기 끝");
        return new ResponseDto<>("프로필 상세보기", profileDetailRespDto);
    }

    @PutMapping("/api/user/{userId}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId,
            @RequestBody ProfileUpdateReqDto profileUpdateReqDto,
            @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        log.debug("디버그 : controller - 프로필 수정 시작");
        if (userId != loginUser.getUser().getId()) { // 권한체크 - 세션의 id, userid가 같으면 수정
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        ProfileUpdateRespDto profileUpdateRespDto = profileService.updateProfile(profileUpdateReqDto, userId);
        log.debug("디버그 : controller - 프로필 수정 끝");
        return new ResponseEntity<>(new ResponseDto<>("프로필 수정", profileUpdateRespDto), HttpStatus.CREATED);
    }
}
