package site.hobbyup.class_final_back.dto.lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.review.Review;
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
      this.lessonDtoList = lessonList.stream().map((lesson) -> new LessonDto(lesson))
          .collect(Collectors.toList());
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
      private Double lessonAvgGrade;
      private boolean isSubscribed; // 로그인 되었을 때만

      public LessonDto(Lesson lesson) {
        this.lessonId = lesson.getId();
        this.lessonName = lesson.getName();
        this.lessonPrice = lesson.getPrice() + "원";
        this.lessonReviewsCount = 0L;
        this.lessonAvgGrade = 0.0;
        this.isSubscribed = false;
      }

    }
  }

  @Setter
  @Getter
  public static class LessonDetailRespDto {
    private String lessonName;
    private Long lessonPrice;
    private Long lessonTime;
    private Long lessonCount;
    private String lessonPlace;
    private String possibleDays;
    private String lessonPolicy;
    private String masterName;
    private String masterIntroduction;
    private List<ReviewDto> lessonReviewList = new ArrayList<>();

    public LessonDetailRespDto(Lesson lesson,
        List<Review> reviewList) {
      this.lessonName = lesson.getName();
      this.lessonPrice = lesson.getPrice();
      this.lessonTime = lesson.getLessonTime();
      this.lessonCount = lesson.getLessonCount();
      this.lessonPlace = lesson.getPlace();
      this.possibleDays = lesson.getPossibleDays();
      this.lessonPolicy = lesson.getPolicy();
      this.lessonReviewList = reviewList.stream().map((review) -> new ReviewDto(review))
          .collect(Collectors.toList());
    }

    @Setter
    @Getter
    public class ReviewDto {
      private String username;
      private String reviewContent;
      private Double lessonGrade;

      public ReviewDto(Review review) {
        this.username = review.getUser().getUsername();
        this.reviewContent = review.getContent();
        this.lessonGrade = review.getGrade();
      }

    }

  }

}
