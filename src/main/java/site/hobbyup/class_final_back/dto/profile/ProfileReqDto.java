package site.hobbyup.class_final_back.dto.profile;

import org.apache.catalina.startup.UserConfig;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.user.User;

public class ProfileReqDto {

    @Setter
    @Getter
    public static class ProfileSaveReqDto {
        private String file;
        private String introduction;
        private String region;
        private String certification;
        private String career_year;
        private String career;
        private User userId;

        public Profile toEntity() {
            return Profile.builder()
                    .file(file)
                    .introduction(introduction)
                    .region(region)
                    .certification(certification)
                    .career_year(career_year)
                    .career(career)
                    .user(userId)
                    .build();
        }
    }

}
