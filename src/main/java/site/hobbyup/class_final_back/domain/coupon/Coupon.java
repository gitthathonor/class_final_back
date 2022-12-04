package site.hobbyup.class_final_back.domain.coupon;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.hobbyup.class_final_back.domain.user.User;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "coupon")
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 20)
    private Long price;
    private Timestamp expiredDate;
    @CreatedDate
    private Timestamp createdAt;

    @ManyToOne
    private User user;

    @Builder
    public Coupon(Long id, String title, Long price, Timestamp expiredDate, Timestamp createdAt, User user) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.expiredDate = expiredDate;
        this.createdAt = createdAt;
        this.user = user;
    }

}
