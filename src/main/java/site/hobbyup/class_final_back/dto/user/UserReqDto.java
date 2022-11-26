package site.hobbyup.class_final_back.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.user.User;

public class UserReqDto {

    @ToString
    @Getter
    @Setter
    public static class JoinReqDto {
        private String username;
        private String password;
        private String email;
        private String phoneNum;
        private UserEnum role;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .phoneNum(phoneNum)
                    .role(role)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginReqDto {
        private String username;
        private String password;
    }
}
