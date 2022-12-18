package site.hobbyup.class_final_back.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileSaveReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileUpdateReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileDetailRespDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileSaveRespDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileUpdateRespDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProfileService extends DecodeUtil {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private static FileOutputStream fos;

    @Transactional
    public ProfileSaveRespDto saveProfile(ProfileSaveReqDto profileSaveReqDto, Long userId) throws IOException {
        log.debug("디버그 : service - 프로필 등록 시작");
        User userPS = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomApiException("유저가 존재하지 않습니다.", HttpStatus.FORBIDDEN));

        Optional<Profile> profileOP = profileRepository.findByUserId(userId);
        if (profileOP.isPresent()) {
            throw new CustomApiException("이미 프로필을 등록했습니다.", HttpStatus.FORBIDDEN);
        }

        Profile profilePS = profileRepository.save(profileSaveReqDto.toEntity(userPS));
        return new ProfileSaveRespDto(profilePS);
    }

    @Transactional
    public ProfileDetailRespDto detailProfile(Long userId) {
        log.debug("디버그 : service - 프로필 상세보기 시작");
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("유저가 존재하지 않습니다.", HttpStatus.FORBIDDEN));

        Optional<Profile> profileOP = profileRepository.findByUserId(userPS.getId());
        if (profileOP.isEmpty()) {
            Profile profile = new Profile(userId, null, null, null, null, null, null, userPS);
            return new ProfileDetailRespDto(profile);
        }

        byte[] decodeByte = Base64.decodeBase64(profileOP.get().getFilePath());
        String filePath = new String(decodeByte);

        profileOP.get().setFilePath(filePath);
        return new ProfileDetailRespDto(profileOP.get());
    }

    @Transactional
    public ProfileUpdateRespDto updateProfile(ProfileUpdateReqDto profileUpdateReqDto, Long userId)
            throws IOException {
        log.debug("디버그 : service - 프로필 수정 시작");
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("유저가 존재하지 않습니다.", HttpStatus.FORBIDDEN));

        // db에 있는 userId 이용해서 프로필 찾기
        Optional<Profile> profileOP = profileRepository.findByUserId(userPS.getId());
        if (profileOP.isEmpty()) {
            throw new CustomApiException("프로필이 존재하지 않습니다.", HttpStatus.FORBIDDEN);
        }

        // 파일 디코딩
        byte[] decodeByte = Base64.decodeBase64(profileUpdateReqDto.getFilePath());
        String filePath = new String(decodeByte);
        profileUpdateReqDto.setFilePath(filePath);

        profileOP.get().update(profileUpdateReqDto);
        return new ProfileUpdateRespDto(profileRepository.save(profileOP.get()));
    }

}
