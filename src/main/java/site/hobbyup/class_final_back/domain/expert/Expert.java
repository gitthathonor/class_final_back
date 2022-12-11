package site.hobbyup.class_final_back.domain.expert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.hobbyup.class_final_back.domain.user.User;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "expert")
@Entity
public class Expert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault(value = "0")
    private Long satisfaction; // 만족도

    @ColumnDefault(value = "0")
    private Long totalLesson; // 총 작업수

    @ColumnDefault(value = "false")
    @Column(name = "is_approval")
    private boolean isApproval; // 레슨 판매 권한 있으면 true

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}
