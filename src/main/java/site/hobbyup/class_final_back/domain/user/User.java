package site.hobbyup.class_final_back.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.AuditingTime;
import site.hobbyup.class_final_back.dto.user.UserReqDto.UserUpdateReqDto;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "users")
@Entity
public class User extends AuditingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;

    @Builder
    public User(Long id, String username, String password, String email, String phoneNum, UserEnum role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.role = role;
    }

    public void update(UserUpdateReqDto userUpdateReqDto) {
        this.password = userUpdateReqDto.getPassword();
        this.email = userUpdateReqDto.getEmail();
        this.phoneNum = userUpdateReqDto.getPhoneNum();
    }

}
