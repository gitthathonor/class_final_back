package site.hobbyup.class_final_back.domain.lesson;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.hobbyup.class_final_back.config.enums.DayEnum;
import site.hobbyup.class_final_back.domain.AuditingTime;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "lesson")
@DynamicInsert
@Entity
public class Lesson extends AuditingTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false)
  private String photo;

  @Column(nullable = false)
  private Long price;

  private String place;

  @ColumnDefault(value = "0")
  private Long lessonTime; // 레슨 시간

  @ColumnDefault(value = "0")
  private Long lessonCount; // 레슨 횟수

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DayEnum possibleDays;

  @Column(columnDefinition = "LONGTEXT")
  private String curriculum;

  @Column(columnDefinition = "LONGTEXT")
  private String policy;

  private Timestamp expiredAt;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;

  @Builder
  public Lesson(Long id, String name, String photo, Long price, String place, Long lessonTime, Long lessonCount,
      DayEnum possibleDays, String curriculum, String policy, Timestamp expiredAt, User user, Category category) {
    this.id = id;
    this.name = name;
    this.photo = photo;
    this.price = price;
    this.place = place;
    this.lessonTime = lessonTime;
    this.lessonCount = lessonCount;
    this.possibleDays = possibleDays;
    this.curriculum = curriculum;
    this.policy = policy;
    this.expiredAt = expiredAt;
    this.user = user;
    this.category = category;
  }

}
