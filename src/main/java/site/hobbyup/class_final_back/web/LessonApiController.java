package site.hobbyup.class_final_back.web;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.service.LessonService;

@RequiredArgsConstructor
@RestController
public class LessonApiController {
  private final LessonService lessonService;

  // 클래스 생성하기
  @Transactional
  public ResponseEntity<?> openLesson() {
    return new ResponseEntity<>(null, null);
  }
  // 클래스 수정하기

  // 클래스 삭제하기

  // 클래스 카테고리별 리스트 보기

  // 클래스
}
