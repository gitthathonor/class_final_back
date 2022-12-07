package site.hobbyup.class_final_back.domain.review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "review")
@Entity
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(columnDefinition = "LONGTEXT")
  private String content;
  private Double grade;

  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @Builder
  public Review(Long id, String content, Double grade, Lesson lesson, User user) {
    this.id = id;
    this.content = content;
    this.grade = grade;
    // this.lesson = lesson;
    this.user = user;
  }

}
