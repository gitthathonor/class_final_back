package site.hobbyup.class_final_back.dto.lesson;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.lesson.Lesson;

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

    public Lesson toEntity(Category category, Expert expert) {
      return Lesson.builder()
          .expert(expert)
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

    public Lesson toEntity(Category category, Expert expert) {
      return Lesson.builder()
          .expert(expert)
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
