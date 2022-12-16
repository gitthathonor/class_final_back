package site.hobbyup.class_final_back.service;

import java.util.Optional;

import org.hibernate.type.TrueFalseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.dto.expert.ExpertRespDto.ExpertPageRespDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExpertService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ProfileRepository profileRepository;
    private final ExpertRepository expertRepository;

    public ExpertPageRespDto getExpertPage(Long userId) {
        Expert expertPS = expertRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomApiException("전문가 등록이 필요합니다.", HttpStatus.BAD_REQUEST));
        Optional<Profile> profileOP = profileRepository.findByUserId(userId);
        if (!profileOP.isPresent()) {
            return new ExpertPageRespDto(expertPS);
        }
        Profile profilePS = profileOP.get();

        return new ExpertPageRespDto(profilePS, expertPS);
    }
}
