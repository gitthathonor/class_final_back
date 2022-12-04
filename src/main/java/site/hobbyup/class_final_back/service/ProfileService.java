package site.hobbyup.class_final_back.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

                // 디코딩해서 이미지 저장하고 경로 리턴
                String filePath = saveDecodingImage(profileSaveReqDto.getFilePath());

                profileSaveReqDto.setFilePath(filePath);
                Profile profilePS = profileRepository.save(profileSaveReqDto
                                .toEntity(userPS));

                log.debug("디버그 : service - 프로필 등록 끝");
                return new ProfileSaveRespDto(profilePS);
        }

        @Transactional
        public ProfileDetailRespDto detailProfile(Long userId) {
                log.debug("디버그 : service - 프로필 상세보기 시작");
                User userPS = userRepository.findById(userId)
                                .orElseThrow(
                                                () -> new CustomApiException("탈퇴한 유저입니다.", HttpStatus.FORBIDDEN));

                Profile profilePS = profileRepository.findByUserId(userPS.getId());
                if (profilePS == null) {
                        throw new CustomApiException("프로필이 존재하지 않습니다.", HttpStatus.FORBIDDEN);
                }

                return new ProfileDetailRespDto(profilePS);
        }

        @Transactional
        public ProfileUpdateRespDto updateProfile(ProfileUpdateReqDto profileUpdateReqDto, Long userId)
                        throws IOException {
                log.debug("디버그 : service - 프로필 수정 시작");
                User userPS = userRepository.findById(userId)
                                .orElseThrow(
                                                () -> new CustomApiException("유저가 존재하지 않습니다.", HttpStatus.FORBIDDEN));

                // db에 있는 userId 이용해서 프로필 찾기
                Profile profilePS = profileRepository.findByUserId(userPS.getId());
                if (profilePS == null) {
                        throw new CustomApiException("프로필이 존재하지 않습니다.", HttpStatus.FORBIDDEN);
                }

                // 파일 디코딩
                byte[] decodeByte = Base64.decodeBase64(profileUpdateReqDto.getFilePath());
                String filePath = "C:\\Temp\\upload\\" + decodeByte + ".jpg";
                // 파일 수정했는지 확인하고 수정됐다면 이미지 새로 저장
                if (profilePS.getFilePath() != filePath) {
                        File file = new File(profilePS.getFilePath());
                        file.delete();
                        fos = new FileOutputStream(filePath);
                        fos.write(decodeByte);
                        fos.close();
                        profileUpdateReqDto.setFilePath(filePath);
                }

                profilePS.update(profileUpdateReqDto);
                log.debug("디버그 : service - 프로필 수정 끝");
                return new ProfileUpdateRespDto(profileRepository.save(profilePS));
        }
}
