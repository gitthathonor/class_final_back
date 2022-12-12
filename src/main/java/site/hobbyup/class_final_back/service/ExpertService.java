package site.hobbyup.class_final_back.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.dto.expert.ExpertRespDto.ExpertPageRespDto;

@RequiredArgsConstructor
@Service
public class ExpertService {

    private final ProfileRepository profileRepository;
    private final ExpertRepository expertRepository;

    public ExpertPageRespDto getExpertPage(Long userId) {
        Profile profilePS = profileRepository.findByUserId(userId)
                .orElse(null);
        Expert expertPS = expertRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomApiException("전문가 등록이 필요합니다.", HttpStatus.BAD_REQUEST));

        return new ExpertPageRespDto(profilePS, expertPS);
    }
}
