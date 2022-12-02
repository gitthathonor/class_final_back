package site.hobbyup.class_final_back.dto.user;

import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.hobbyup.class_final_back.domain.interest.Interest;
import site.hobbyup.class_final_back.domain.user.User;

public class UserRespDto {

    @ToString
    @Getter
    @Setter
    public static class JoinRespDto {
        private Long id;
        private String username;
        private List<Interest> interestList;

        public JoinRespDto(User user, List<Interest> interestList) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.interestList = interestList;
        }

        // @Setter
        // @Getter
        // public class InterestDto {
        // private
        // }
    }

    @Setter
    @Getter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    @Setter
    @Getter
    public static class UserUpdateRespDto {
        private Long id;
        private String username;
        private String email;
        private String phoneNum;

        public UserUpdateRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.phoneNum = user.getPhoneNum();
        }

    }
}
