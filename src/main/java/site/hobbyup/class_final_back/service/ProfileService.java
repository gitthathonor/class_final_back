package site.hobbyup.class_final_back.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileSaveReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileRespDto.ProfileSaveRespDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProfileSaveRespDto saveProfile(ProfileSaveReqDto profileSaveReqDto) {
        log.debug("디버그 : service - 프로필 등록 시작");
        log.debug("디버그 : " + profileSaveReqDto.getUserId());
        User userPS = userRepository.findById(profileSaveReqDto.getUserId())
                .orElseThrow(
                        () -> new RuntimeException("유저가 존재하지 않습니다."));
        log.debug("디버그 : 유저찾기");
        Profile profilePS = profileRepository.save(profileSaveReqDto.toEntity(userPS));
        log.debug("디버그 : service - 프로필 등록 끝");
        return new ProfileSaveRespDto(profilePS);
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

}