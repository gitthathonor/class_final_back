package site.hobbyup.class_final_back.oauth.dto;

import javax.persistence.Column;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.hobbyup.class_final_back.domain.user.User;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoRespDto {
    @Column(nullable = false, length = 100, unique = true)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    private String oauth;

    public User toEntity(String username, String password, String email, String oauth) {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .oauth(oauth)
                .build();

    }
}
