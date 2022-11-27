package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileSaveReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileDetailRespDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileSaveRespDto;
import site.hobbyup.class_final_back.service.ProfileService;

@RequiredArgsConstructor
@RestController
public class ProfileApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProfileService profileService;

    @PostMapping("/api/profile")
    public ResponseDto<?> profileSave(@RequestBody ProfileSaveReqDto profileSaveReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : controller - 프로필 등록 시작");
        profileSaveReqDto.setUserId(loginUser.getUser());
        ProfileSaveRespDto profileSaveRespDto = profileService.profileSave(profileSaveReqDto);
        log.debug("디버그 : controller - 프로필 등록 끝");
        return new ResponseDto<>("프로필 등록", profileSaveRespDto);
    }
    // @PostMapping("/api/profile")
    // public ResponseDto<?> profileSave(@RequestPart("file") MultipartFile image,
    // @RequestPart("profile") Profile profile) {
    // try {
    // Base64.Encoder encoder = Base64.getEncoder();
    // byte[] photoEncode = encoder.encode(image.getBytes());
    // String img = new String(photoEncode, "UTF8");
    // profile.setFile(img);
    // profileRepository.save(profile);
    // return new ResponseDto<>(1, "프로필 등록", null);
    // } catch (Exception e) {
    // throw new RuntimeException("error");
    // }
    // }

    @GetMapping("/api/profile")
    public ResponseDto<?> profile(@AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : controller - 프로필 상세보기 시작");
        ProfileDetailRespDto profileDetailRespDto = profileService.profile(loginUser.getUser().getId());
        log.debug("디버그 : controller - 프로필 상세보기 끝");
        return new ResponseDto<>("프로필 상세보기", profileDetailRespDto);
    }

}
