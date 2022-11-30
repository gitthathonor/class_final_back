package site.hobbyup.class_final_back.dto.lesson;

import java.sql.Timestamp;

public class LessonReqDto {
  public static class LessonSaveReqDto {
    private String name;
    private String photo;
    private Long price;
    private String place;
    private String curriculum;
    private String policy;
    private Timestamp expiredAt;
  }
}
