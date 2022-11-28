package site.hobbyup.class_final_back.domain.profile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.hobbyup.class_final_back.domain.AuditingTime;
import site.hobbyup.class_final_back.domain.user.User;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "profile")
@Entity
public class Profile extends AuditingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "LONGTEXT")
    private String file;
    private String introduction;
    private String region;
    private String certification;
    private String career_year;
    private String career;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Profile(Long id, String file, String introduction, String region, String certification, String career_year,
            String career, User user) {
        this.id = id;
        this.file = file;
        this.introduction = introduction;
        this.region = region;
        this.certification = certification;
        this.career_year = career_year;
        this.career = career;
        this.user = user;
    }

}
