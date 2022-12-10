package site.hobbyup.class_final_back.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.AuditingTime;
import site.hobbyup.class_final_back.dto.user.UserReqDto.UserUpdateReqDto;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Entity
public class User extends AuditingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    @Column(nullable = false, length = 60)
    private String password;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false)
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;

    @Column(name = "is_disabled")
    private boolean isDisabled; // default가 false, 탈퇴하면 true

    @Builder
    public User(Long id, String username, String password, String email, String phoneNum, UserEnum role,
            boolean isDisabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.role = role;
        this.isDisabled = isDisabled;
    }

    public void update(UserUpdateReqDto userUpdateReqDto) {
        this.password = userUpdateReqDto.getPassword();
        this.email = userUpdateReqDto.getEmail();
        this.phoneNum = userUpdateReqDto.getPhoneNum();
    }

    public void delete() {
        this.isDisabled = true;
    }

}
