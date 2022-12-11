package site.hobbyup.class_final_back.domain.subscribe;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.hobbyup.class_final_back.domain.AuditingTime;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.user.User;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "subscribe", uniqueConstraints = {
        @UniqueConstraint(name = "subscribeId", columnNames = { "userId", "lessonId" }) })
@Entity
public class Subscribe extends AuditingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonId")
    private Lesson lesson;

    @Builder
    public Subscribe(Long id, User user, Lesson lesson) {
        this.id = id;
        this.user = user;
        this.lesson = lesson;
    }

    public Subscribe entity(User user, Lesson lesson) {
        return Subscribe.builder()
                .user(user)
                .lesson(lesson)
                .build();
    }

}
