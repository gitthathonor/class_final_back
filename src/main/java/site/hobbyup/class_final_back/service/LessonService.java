package site.hobbyup.class_final_back.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;

@RequiredArgsConstructor
@Service
public class LessonService {
  private final LessonRepository lessonRepository;

  // 클래스 생성하기
  public void openLesson() {
    lessonRepository.save(null);
  }

  // 클래스 수정하기

  // 클래스 삭제하기
}
