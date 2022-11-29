package site.hobbyup.class_final_back.service;

import java.io.FileOutputStream;
import java.io.IOException;

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
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileSaveRespDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProfileService extends DecodeUtil {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProfileSaveRespDto saveProfile(ProfileSaveReqDto profileSaveReqDto) throws IOException {
        log.debug("디버그 : service - 프로필 등록 시작");

        User userPS = userRepository.findById(profileSaveReqDto.getUserId())
                .orElseThrow(
                        () -> new CustomApiException("가입되지 않은 유저입니다.", HttpStatus.FORBIDDEN));

        // 디코딩해서 이미지 저장하고 경로 리턴
        String filePath = saveDecodingImage(profileSaveReqDto.getFilePath());

        profileSaveReqDto.setFilePath(filePath);
        Profile profilePS = profileRepository.save(profileSaveReqDto
                .toEntity(userPS));
        log.debug("디버그 : service - 프로필 등록 끝");
        return new ProfileSaveRespDto(profilePS);
    }

}

// @Transactional
// public ProfileDetailRespDto profile(Long id) {
// log.debug("디버그 : service - 프로필 상세보기 시작");
// Optional<Profile> profileOP = profileRepository.findById(id);
// if (profileOP.isPresent()) {
// log.debug("디버그 : service - 프로필 상세보기 끝");
// return new ProfileDetailRespDto(profileOP.get());
// } else {
// throw new RuntimeException("해당 " + id + " 를 찾을 수 없습니다.");
// }
// }

// @Transactional
// public ProfileUpdateRespDto updateProfile(ProfileUpdateReqDto
// profileUpdateReqDto) throws IOException {
// log.debug("디버그 : service - 프로필 수정 시작");

// User userPS = userRepository.findById(profileUpdateReqDto.getUserId())
// .orElseThrow(
// () -> new RuntimeException("유저가 존재하지 않습니다."));
// log.debug("디버그 : 유저찾기");
// Profile profilePS = profileRepository.findById(profileUpdateReqDto.getId());
// ProfileUpdateRespDto profileUpdateRespDto = new
// ProfileUpdateRespDto(profilePS);
// profilePS.update(profileUpdateRespDto);

// // base64 디코딩
// String encodeFile = profileUpdateReqDto.getFilePath();
// byte[] stringToByte = encodeFile.getBytes();
// byte[] decodeByte = Base64.decodeBase64(stringToByte);

// // 기존 이미지 삭제
// // 이미지 저장
// fos = new FileOutputStream("C:\\Temp\\upload\\image.jpg"); // 현위치에 path명으로
// 파일생성
// fos.write(decodeByte, 0, decodeByte.length); // 파일에 buffer의 모든 내용 출력
// fos.close();

// log.debug("디버그 : service - 프로필 등록 끝");
// return new ProfileSaveRespDto(profilePS);
// }
