package site.hobbyup.class_final_back.dto.profile;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.user.User;

public class ProfileRespDto {

    @Setter
    @Getter
    public static class ProfileSaveRespDto {
        private Long id;
        private UserDto userId;

        @Setter
        @Getter
        public static class UserDto {
            private Long id;

            public UserDto(User user) {
                this.id = user.getId();
            }
        }

        public ProfileSaveRespDto(Profile profile) {
            this.id = profile.getId();
            this.userId = new UserDto(profile.getUser());
        }
    }

    @Setter
    @Getter
    public static class ProfileDetailRespDto {
        private Long id;
        private UserDto user;

        @Setter
        @Getter
        public static class UserDto {
            private Long id;
            private String username;

            public UserDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

        public ProfileDetailRespDto(Profile profile) {
            this.id = profile.getId();
            this.user = new UserDto(profile.getUser());
        }
    }

    // @Setter
    // @Getter
    // public static class ProfileSaveRespDto {
    // private Long id;
    // private String filePath;
    // private String introduction;
    // private String region;
    // private String certification;
    // private String careerYear;
    // private String career;
    // private UserDto user;

    // public ProfileSaveRespDto(Profile profile) {
    // this.id = profile.getId();
    // this.filePath = profile.getFilePath();
    // this.introduction = profile.getIntroduction();
    // this.region = profile.getRegion();
    // this.certification = profile.getCertification();
    // this.careerYear = profile.getCareerYear();
    // this.career = profile.getCareer();
    // this.user = new UserDto(profile.getUser());
    // }

    // @Setter
    // @Getter
    // public static class UserDto {
    // private Long id;
    // private String username;

    // public UserDto(User user) {
    // this.id = user.getId();
    // this.username = user.getUsername();
    // }
    // }

}
