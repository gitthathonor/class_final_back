package site.hobbyup.class_final_back.dto.lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonCategoryListRespDto.LessonDto;

public class LessonRespDto {
  @Setter
  @Getter
  public static class LessonSaveRespDto {
    private Long id;
    private String name;
    private String categoryName;
    private Long expertId;
    private List<String> possibleDays = new ArrayList<>();
    private String username;

    public LessonSaveRespDto(Lesson lesson, List<String> dayList) {
      this.id = lesson.getId();
      this.name = lesson.getName();
      this.categoryName = lesson.getCategory().getName();
      this.expertId = lesson.getExpert().getId();
      this.possibleDays = dayList;
      this.username = lesson.getExpert().getUser().getUsername();
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
    private LessonDto lessonDto;
    private ProfileDto profileDto;
    private Double lessonAvgGrade;
    private boolean isSubscribed;
    private List<ReviewDto> lessonReviewList = new ArrayList<>();

    public LessonDetailRespDto(Lesson lesson, List<String> dayList, Profile profile, Double avgGrade,
        boolean isSubscribed,
        List<Review> reviewList) {
      this.lessonDto = new LessonDto(lesson, dayList);
      this.profileDto = new ProfileDto(profile);
      this.lessonAvgGrade = avgGrade;
      this.isSubscribed = isSubscribed;
      this.lessonReviewList = reviewList.stream().map((review) -> new ReviewDto(review))
          .collect(Collectors.toList());
    }

    @Setter
    @Getter
    public class LessonDto {
      private String lessonName;
      private Long lessonPrice;
      private Long lessonTime;
      private Long lessonCount;
      private String curriculum;
      private String lessonPlace;
      private List<String> possibleDays;
      private String lessonPolicy;

      public LessonDto(Lesson lesson, List<String> dayList) {
        this.lessonName = lesson.getName();
        this.lessonPrice = lesson.getPrice();
        this.lessonTime = lesson.getLessonTime();
        this.lessonCount = lesson.getLessonCount();
        this.curriculum = lesson.getCurriculum();
        this.lessonPlace = lesson.getPlace();
        this.possibleDays = dayList;
        this.lessonPolicy = lesson.getPolicy();
      }

    }

    @Setter
    @Getter
    public class ProfileDto {
      private String expertPhoto;
      private String expertIntroduction;

      public ProfileDto(Profile profile) {
        this.expertPhoto = profile.getFilePath();
        this.expertIntroduction = profile.getIntroduction();
      }
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

  @Setter
  @Getter
  public static class LessonLatestListRespDto {
    List<LessonLatestRespDto> lessonList = new ArrayList<>();

    public LessonLatestListRespDto(List<Lesson> lessonList) {
      this.lessonList = lessonList.stream().map(
          (lesson) -> new LessonLatestRespDto(lesson))
          .collect(Collectors.toList());
    }
  }

  @Setter
  @Getter
  public static class LessonLatestRespDto {
    private String name;
    private Long price;
    private String photo;

    public LessonLatestRespDto(Lesson lesson) {
      this.name = lesson.getName();
      this.price = lesson.getPrice();
      this.photo = lesson.getPhoto();
    }
  }

  @Setter
  @Getter
  public static class LessonUpdateRespDto {
    private Long id;
    private String name;
    private String categoryName;
    private Long expertId;
    private String username;

    public LessonUpdateRespDto(Lesson lesson) {
      this.id = lesson.getId();
      this.name = lesson.getName();
      this.categoryName = lesson.getCategory().getName();
      this.expertId = lesson.getExpert().getId();
      this.username = lesson.getExpert().getUser().getUsername();
    }
  }

}
