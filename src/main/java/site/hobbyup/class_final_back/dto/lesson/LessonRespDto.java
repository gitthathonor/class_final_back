package site.hobbyup.class_final_back.dto.lesson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.user.User;

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

  // @Setter
  // @Getter
  // public static class LessonCategoryListRespDto {
  // private CategoryDto categoryDto;
  // private List<LessonDto> lessonDtoList;

  // public LessonCategoryListRespDto(Category category, List<Lesson> lessonList)
  // {
  // this.categoryDto = new CategoryDto(category);
  // this.lessonDtoList = lessonList.stream().map((lesson) -> new
  // LessonDto(lesson))
  // .collect(Collectors.toList());
  // }

  // @Setter
  // @Getter
  // public class CategoryDto {
  // private String categoryName;

  // public CategoryDto(Category category) {
  // this.categoryName = category.getName();
  // }

  // }

  // @Setter
  // @Getter
  // public class LessonDto {
  // private Long lessonId;
  // private String lessonName;
  // private String lessonPrice;
  // private Long lessonReviewsCount;
  // private Double lessonAvgGrade;
  // private boolean isSubscribed; // 로그인 되었을 때만

  // public LessonDto(Lesson lesson) {
  // this.lessonId = lesson.getId();
  // this.lessonName = lesson.getName();
  // this.lessonPrice = lesson.getPrice() + "원";
  // this.lessonReviewsCount = 0L;
  // this.lessonAvgGrade = 0.0;
  // this.isSubscribed = false;
  // }

  // }
  // }

  @Setter
  @Getter
  public static class LessonDetailRespDto {
    private LessonDto lessonDto;
    private ProfileDto profileDto;
    private Double lessonAvgGrade;
    private Long lessonTotalReviewsCount;
    private boolean isSubscribed;
    private List<ReviewDto> lessonReviewList = new ArrayList<>();

    public LessonDetailRespDto(Lesson lesson, List<String> dayList, Profile profile, Double avgGrade,
        Long lessonTotalReviewsCount,
        boolean isSubscribed,
        List<Review> reviewList) {
      this.lessonDto = new LessonDto(lesson, dayList);
      this.profileDto = new ProfileDto(profile);
      this.lessonAvgGrade = avgGrade;
      this.lessonTotalReviewsCount = lessonTotalReviewsCount;
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
      private String expertName;
      private String expertIntroduction;

      public ProfileDto(Profile profile) {
        this.expertPhoto = profile.getFilePath();
        this.expertName = profile.getUser().getUsername();
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

  // @Setter
  // @Getter
  // public static class LessonLatestListRespDto {
  // List<LessonLatestRespDto> lessonList = new ArrayList<>();

  // public LessonLatestListRespDto(List<Lesson> lessonList) {
  // this.lessonList = lessonList.stream().map(
  // (lesson) -> new LessonLatestRespDto(lesson))
  // .collect(Collectors.toList());
  // }
  // }

  // @Setter
  // @Getter
  // public static class LessonLatestRespDto {
  // private String name;
  // private Long price;
  // private String photo;

  // public LessonLatestRespDto(Lesson lesson) {
  // this.name = lesson.getName();
  // this.price = lesson.getPrice();
  // this.photo = lesson.getPhoto();
  // }
  // }

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

  @NoArgsConstructor
  @AllArgsConstructor
  @Setter
  @Getter
  public static class LessonCategoryListRespDto {
    private Long lessonId;
    private String lessonName;
    private Long lessonPrice;
    private Double avgGrade;
    private Long totalReviews;
    private boolean isSubscribed;

    public LessonCategoryListRespDto(String msg) {
      this.lessonName = msg;
    }

    public LessonCategoryListRespDto(BigInteger lessonId, String lessonName, BigInteger lessonPrice,
        BigInteger avgGrade,
        BigDecimal totalReviews,
        Boolean isSubscribed, Boolean recommand, BigInteger ranking, Timestamp recent) {
      this.lessonId = lessonId.longValue();
      this.lessonName = lessonName;
      this.lessonPrice = lessonPrice.longValue();
      this.avgGrade = avgGrade.doubleValue();
      this.totalReviews = totalReviews.longValue();
      this.isSubscribed = isSubscribed.booleanValue();
    }

  }

  @Setter
  @Getter
  public static class LessonSearchListRespDto {
    private Long lessonId;
    private String lessonName;
    private Long lessonPrice;
    private Double avgGrade;
    private Long totalReviews;
    private boolean isSubscribed;
    private boolean recommand;
    private Long ranking;
    private String recent;

    public LessonSearchListRespDto(BigInteger lessonId, String lessonName, BigInteger lessonPrice,
        BigInteger avgGrade,
        BigDecimal totalReviews,
        Boolean isSubscribed, Boolean recommand, BigInteger ranking, Timestamp recent) {
      this.lessonId = lessonId.longValue();
      this.lessonName = lessonName;
      this.lessonPrice = lessonPrice.longValue();
      this.avgGrade = avgGrade.doubleValue();
      this.totalReviews = totalReviews.longValue();
      this.isSubscribed = isSubscribed.booleanValue();
      this.recommand = recommand.booleanValue();
      this.ranking = ranking.longValue();
      this.recent = recent.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

  }

  // 찜한 클래스 목록보기
  @Setter
  @Getter
  public static class LessonSubscribedListRespDto {
    private Long lessonId;
    private String lessonName;
    private Long lessonPrice;
    private Double avgGrade;
    private Long totalReviews;
    private boolean isSubscribed;

    public LessonSubscribedListRespDto(BigInteger lessonId, String lessonName, BigInteger lessonPrice,
        BigInteger avgGrade,
        BigDecimal totalReviews,
        Boolean isSubscribed) {
      this.lessonId = lessonId.longValue();
      this.lessonName = lessonName;
      this.lessonPrice = lessonPrice.longValue();
      this.avgGrade = avgGrade.doubleValue();
      this.totalReviews = totalReviews.longValue();
      this.isSubscribed = isSubscribed;
    }

  }

  @Setter
  @Getter
  public static class LessonSellingByExpertRespDto {
    private ExpertDto expertDto;
    private List<LessonDto> lessonDtoList = new ArrayList<>();

    public LessonSellingByExpertRespDto(Expert expert) {
      this.expertDto = new ExpertDto(expert);
      this.lessonDtoList = expert.getLessonList().stream().map((lesson) -> new LessonDto(lesson))
          .collect(Collectors.toList());
    }

    @Setter
    @Getter
    public class ExpertDto {
      private String expertName;

      public ExpertDto(Expert expert) {
        this.expertName = expert.getUser().getUsername();
      }

    }

    @Setter
    @Getter
    public class LessonDto {
      private Long lessonId;
      private String lessonPhoto;
      private String lessonName;
      private Long lessonPrice;
      private String lessonDeadLine;

      public LessonDto(Lesson lesson) {
        this.lessonId = lesson.getId();
        this.lessonPhoto = lesson.getPhoto();
        this.lessonName = lesson.getName();
        this.lessonPrice = lesson.getPrice();
        this.lessonDeadLine = lesson.getDeadline().toLocalDateTime()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ;
      }

    }
  }

  @Setter
  @Getter
  public static class LessonBuyingByUserRespDto {
    private Long lessonId;
    private String lessonPhoto;
    private String lessonName;
    private Long lessonPrice;
    private String lessonDeadline;

    public LessonBuyingByUserRespDto(BigInteger lessonId, String lessonPhoto, String lessonName, BigInteger lessonPrice,
        Timestamp lessonDeadline) {
      this.lessonId = lessonId.longValue();
      this.lessonPhoto = lessonPhoto;
      this.lessonName = lessonName;
      this.lessonPrice = lessonPrice.longValue();
      this.lessonDeadline = lessonDeadline.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

  }

  @Setter
  @Getter
  public static class LessonReviewDto {
    private Long lessonId;
    private String lessonPhoto;
    private String lessonName;
    private Long lessonPrice;
    private String lessonDeadline;

    public LessonReviewDto(Lesson lesson) {
      this.lessonId = lesson.getId();
      this.lessonPhoto = lesson.getPhoto();
      this.lessonName = lesson.getName();
      this.lessonPrice = lesson.getPrice();
      this.lessonDeadline = lesson.getDeadline().toLocalDateTime()
          .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

  }

}
