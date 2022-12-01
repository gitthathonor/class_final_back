package site.hobbyup.class_final_back.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.user.User;

public class LessonRespDto {
  @Setter
  @Getter
  public static class LessonSaveRespDto {
    private Long id;
    private String name;
    private Category category;
    private User user;

    public LessonSaveRespDto(Lesson lesson) {
      this.id = lesson.getId();
      this.name = lesson.getName();
      this.category = lesson.getCategory();
      this.user = lesson.getUser();
    }
  }
}
