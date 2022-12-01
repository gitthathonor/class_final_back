package site.hobbyup.class_final_back.dto.lesson;

import java.util.List;
import java.util.stream.Collectors;

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

  @Setter
  @Getter
  public static class LessonCategoryListRespDto {
    private CategoryDto categoryDto;
    private List<LessonDto> lessonDtoList;

    public LessonCategoryListRespDto(Category category, List<Lesson> lessonList) {
      this.categoryDto = new CategoryDto(category);
      this.lessonDtoList = lessonList.stream().map((lesson) -> new LessonDto(lesson)).collect(Collectors.toList());
    }

    @Setter
    @Getter
    public class CategoryDto {
      private String categoryName;

      public CategoryDto(Category category) {
        this.categoryName = category.getName();
      }

    }

    @Setter
    @Getter
    public class LessonDto {
      private Long lessonId;
      private String lessonName;
      private String lessonPrice;
      private Long lessonReviewsCount;
      private boolean isSubscribed; // 로그인 되었을 때만

      public LessonDto(Lesson lesson) {
        this.lessonId = lesson.getId();
        this.lessonName = lesson.getName();
        this.lessonPrice = lesson.getPrice() + "원";
        this.lessonReviewsCount = 0L;
        this.isSubscribed = false;
      }

    }
  }

}
