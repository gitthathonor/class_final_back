package site.hobbyup.class_final_back.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileSaveReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileSaveRespDto;
import site.hobbyup.class_final_back.service.ProfileService;

@RequiredArgsConstructor
@RestController
public class ProfileApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProfileService profileService;

    @PostMapping("/api/profile")
    public ResponseEntity<?> profileSave(@RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "profileSaveReqDto") ProfileSaveReqDto profileSaveReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : controller - 프로필 등록 시작");

        // base64 인코딩
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] photoEncode = encoder.encode(file.getBytes());
            String fileEncode = new String(photoEncode, "UTF8");
            profileSaveReqDto.setFile(fileEncode);
        } catch (Exception e) {
            throw new RuntimeException("error");
        }
        profileSaveReqDto.setUserId(loginUser.getUser().getId());
        log.debug("디버그 : service전달");
        log.debug("디버그 : " + profileSaveReqDto.getUserId());
        ProfileSaveRespDto profileSaveRespDto = profileService.saveProfile(profileSaveReqDto);
        log.debug("디버그 : controller - 프로필 등록 끝");
        return new ResponseEntity<>(new ResponseDto<>("프로필 등록", profileSaveRespDto), HttpStatus.CREATED);

    }

    // @GetMapping("/api/profile")
    // public ResponseDto<?> profile(@AuthenticationPrincipal LoginUser loginUser) {
    // log.debug("디버그 : controller - 프로필 상세보기 시작");
    // ProfileDetailRespDto profileDetailRespDto =
    // profileService.profile(loginUser.getUser().getId());
    // log.debug("디버그 : controller - 프로필 상세보기 끝");
    // return new ResponseDto<>("프로필 상세보기", profileDetailRespDto);
    // }
}
