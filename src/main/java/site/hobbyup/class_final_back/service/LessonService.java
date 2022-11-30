package site.hobbyup.class_final_back.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LessonService {
  private final LessonRepository lessonRepository;

  // 클래스 생성하기
  @Transactional
  public LessonSaveRespDto saveLesson(LessonSaveReqDto lessonSaveReqDto) {
    Lesson lessonPS = lessonRepository.save(lessonSaveReqDto.toEntity());
    return new LessonSaveRespDto(lessonPS);
  }

  // 클래스 수정하기

  // 클래스 삭제하기
}
