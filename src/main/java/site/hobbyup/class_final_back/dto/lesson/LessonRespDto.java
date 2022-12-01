package site.hobbyup.class_final_back.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.lesson.Lesson;

public class LessonRespDto {
  @Setter
  @Getter
  public static class LessonSaveRespDto {
    private Long id;
    private String name;
    private Category category;

    public LessonSaveRespDto(Lesson lesson) {
      this.id = lesson.getId();
      this.name = lesson.getName();
      this.category = lesson.getCategory();
    }
  }
}
