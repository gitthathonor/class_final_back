package site.hobbyup.class_final_back.dto.review;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.review.Review;

public class ReviewRespDto {
  @Setter
  @Getter
  public static class ReviewSaveRespDto {
    private Long id;
    private String content;
    private Double grade;

    public ReviewSaveRespDto(Review review) {
      this.id = review.getId();
      this.content = review.getContent();
      this.grade = review.getGrade();
    }

  }
}
