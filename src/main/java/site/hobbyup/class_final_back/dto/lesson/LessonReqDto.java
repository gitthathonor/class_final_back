package site.hobbyup.class_final_back.dto.lesson;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.lesson.Lesson;

public class LessonReqDto {
  @Setter
  @Getter
  public static class LessonSaveReqDto {
    private String name;
    private String photo;
    private Long price;
    private String place;
    private String possibleDays;
    private String curriculum;
    private String policy;
    private Timestamp expiredAt;
    private Category category;

    public Lesson toEntity() {
      return Lesson.builder()
          .name(name)
          .photo(photo)
          .price(price)
          .place(place)
          .possibleDays(possibleDays)
          .curriculum(curriculum)
          .policy(policy)
          .expiredAt(expiredAt)
          .build();
    }
  }
}
