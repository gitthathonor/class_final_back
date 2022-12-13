package site.hobbyup.class_final_back.dto.lesson;

public interface LessonCategoryListDto {
  String getLessonName();

  Long getLessonPrice();

  Double getAvgGrade();

  Long getTotalReviews();

  boolean isSubscribed();

}
