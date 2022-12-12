package site.hobbyup.class_final_back.dto.expert;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.profile.Profile;

public class ExpertRespDto {

    @Setter
    @Getter
    public static class ExpertPageRespDto {
        private String profilePhoto;
        private String username;
        private Long satisfaction;
        private Long totalLesson;

        public ExpertPageRespDto(Profile profile, Expert expert) {
            this.profilePhoto = profile.getFilePath();
            this.username = profile.getUser().getUsername();
            this.satisfaction = expert.getSatisfaction();
            this.totalLesson = expert.getTotalLesson();
        }

    }
}
