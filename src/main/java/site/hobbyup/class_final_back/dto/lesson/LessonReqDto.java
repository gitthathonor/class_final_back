package site.hobbyup.class_final_back.dto.lesson;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.user.User;

public class LessonReqDto {
  @Setter
  @Getter
  public static class LessonSaveReqDto {
    private String name;
    private String photo;
    private Long price;
    private String place;
    private Long lessonTime;
    private Long lessonCount;
    private String possibleDays;
    private String curriculum;
    private String policy;
    private Timestamp deadline;
    private Long categoryId;

    public Lesson toEntity(Category category, User user) {
      return Lesson.builder()
          .user(user)
          .name(name)
          .photo(photo)
          .price(price)
          .place(place)
          .lessonTime(lessonTime)
          .lessonCount(lessonCount)
          .possibleDays(possibleDays)
          .curriculum(curriculum)
          .policy(policy)
          .deadline(deadline)
          .category(category)
          .build();
    }
  }

  @Setter
  @Getter
  public static class LessonUpdateReqDto {
    private String name;
    private String photo;
    private Long price;
    private String place;
    private Long lessonTime;
    private Long lessonCount;
    private String possibleDays;
    private String curriculum;
    private String policy;
    private Timestamp deadline;
    private Long categoryId;

    public Lesson toEntity(Category category, User user) {
      return Lesson.builder()
          .user(user)
          .name(name)
          .photo(photo)
          .price(price)
          .place(place)
          .lessonTime(lessonTime)
          .lessonCount(lessonCount)
          .possibleDays(possibleDays)
          .curriculum(curriculum)
          .policy(policy)
          .deadline(deadline)
          .category(category)
          .build();
    }
  }

}
