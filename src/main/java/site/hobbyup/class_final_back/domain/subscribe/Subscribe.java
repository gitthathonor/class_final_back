package site.hobbyup.class_final_back.domain.subscribe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.hobbyup.class_final_back.domain.AuditingTime;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.lesson.Lesson;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "subscribe")
@Entity
public class Subscribe extends AuditingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    @Builder
    public Subscribe(Long id, User user, Lesson lesson) {
        this.id = id;
        this.user = user;
        this.lesson = lesson;
    }

}
