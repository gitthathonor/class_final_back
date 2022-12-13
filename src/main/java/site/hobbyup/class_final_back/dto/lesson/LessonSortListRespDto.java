package site.hobbyup.class_final_back.dto.lesson;

public interface LessonSortListRespDto {
  String getLessonName();

  Long getLessonPrice();

  Double getAvgGrade();

  Long getTotalReviews();

  boolean isSubscribed();

  Long getRecommand();

  Long getRanking();

  String getRecent();

}
