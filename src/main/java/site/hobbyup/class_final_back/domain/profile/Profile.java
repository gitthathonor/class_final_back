package site.hobbyup.class_final_back.domain.profile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.hobbyup.class_final_back.domain.AuditingTime;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileUpdateReqDto;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "profile")
@Entity
public class Profile extends AuditingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String filePath;
    @Column(nullable = false, length = 50)
    private String introduction;
    @Column(nullable = false, length = 20)
    private String region;
    @Column(nullable = false, length = 50)
    private String certification;
    @Column(nullable = false, length = 20)
    private String careerYear;
    @Column(nullable = false, length = 50)
    private String career;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @Builder
    public Profile(Long id, String filePath, String introduction, String region, String certification,
            String careerYear,
            String career, User user) {
        this.id = id;
        this.filePath = filePath;
        this.introduction = introduction;
        this.region = region;
        this.certification = certification;
        this.careerYear = careerYear;
        this.career = career;
        this.user = user;
    }

    public void update(ProfileUpdateReqDto profileUpdateReqDto) {
        this.filePath = profileUpdateReqDto.getFilePath();
        this.introduction = profileUpdateReqDto.getIntroduction();
        this.region = profileUpdateReqDto.getRegion();
        this.certification = profileUpdateReqDto.getCertification();
        this.careerYear = profileUpdateReqDto.getCareerYear();
        this.career = profileUpdateReqDto.getCareer();
    }

}
