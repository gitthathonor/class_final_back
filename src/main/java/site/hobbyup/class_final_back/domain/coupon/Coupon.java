package site.hobbyup.class_final_back.domain.coupon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.hobbyup.class_final_back.domain.AuditingTime;
import site.hobbyup.class_final_back.domain.user.User;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "coupon")
@Entity
public class Coupon extends AuditingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 20)
    private Long price;
    private String expiredDate;

    @ManyToOne
    private User user;

    @Builder
    public Coupon(Long id, String title, Long price, String expiredDate, User user) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.expiredDate = expiredDate;
        this.user = user;
    }

}
