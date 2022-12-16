package site.hobbyup.class_final_back.dto.expert;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.profile.Profile;

public class ExpertRespDto {

    @Setter
    @Getter
    public static class ExpertPageRespDto {
        private String profilePhoto; // null이면 보낼 디폴트 사진파일이 필요하다.
        private String username;
        private Long satisfaction;
        private Long totalLesson;
        private boolean isApproval;

        public ExpertPageRespDto(Profile profile, Expert expert) {
            this.profilePhoto = profile.getFilePath();
            this.username = expert.getUser().getUsername();
            this.satisfaction = expert.getSatisfaction();
            this.totalLesson = expert.getTotalLesson();
            this.isApproval = expert.isApproval();
        }

        public ExpertPageRespDto(Expert expert) {
            this.username = expert.getUser().getUsername();
            this.satisfaction = expert.getSatisfaction();
            this.totalLesson = expert.getTotalLesson();
            this.isApproval = expert.isApproval();
        }

    }
}
