package site.hobbyup.class_final_back.dto.lesson;

public interface LessonCommonListDto {
  String getLessonName();

  Long getLessonPrice();

  Long getTotalReview();

  Double getAvgGrade();

  Boolean isSubscribed();
}
