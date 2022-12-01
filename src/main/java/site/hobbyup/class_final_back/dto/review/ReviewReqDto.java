package site.hobbyup.class_final_back.dto.review;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.user.User;

public class ReviewReqDto {

  @Setter
  @Getter
  public static class ReviewSaveReqDto {
    private String content;
    private Double grade;

    public Review toEntity(User user, Lesson lesson) {
      return Review.builder()
          .content(content)
          .grade(grade)
          .user(user)
          .lesson(lesson)
          .build();
    }
  }
}
