package site.hobbyup.class_final_back.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.lesson.Lesson;

public class LessonRespDto {
  @Setter
  @Getter
  public static class LessonSaveRespDto {
    private Long id;
    private String name;
    private Long categoryId;

    public LessonSaveRespDto(Lesson lesson) {
      this.id = lesson.getId();
      this.name = lesson.getName();
      this.categoryId = lesson.getCategory().getId();
    }
  }
}
