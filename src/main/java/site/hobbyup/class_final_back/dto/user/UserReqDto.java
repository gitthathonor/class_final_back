package site.hobbyup.class_final_back.dto.user;

import java.util.List;

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
        private List<Long> categoryIds;

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

    @Setter
    @Getter
    public static class UserUpdateReqDto {
        private String password;
        private String email;
        private String phoneNum;
        private List<Long> categoryIds;

        public User toEntity() {
            return User.builder()
                    .password(password)
                    .email(email)
                    .phoneNum(phoneNum)
                    .build();
        }
    }
}
