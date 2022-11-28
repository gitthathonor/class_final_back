package site.hobbyup.class_final_back.dto.profile;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.user.User;

public class ProfileReqDto {

    @Setter
    @Getter
    public static class ProfileSaveReqDto {
        private String filePath;
        private String introduction;
        private String region;
        private String certification;
        private String careerYear;
        private String career;
        private Long userId;

        public Profile toEntity(User user) {
            return Profile.builder()
                    .filePath(filePath)
                    .introduction(introduction)
                    .region(region)
                    .certification(certification)
                    .careerYear(careerYear)
                    .career(career)
                    .user(user)
                    .build();
        }
    }
}
