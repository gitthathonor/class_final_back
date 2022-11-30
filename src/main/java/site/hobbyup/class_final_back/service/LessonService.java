package site.hobbyup.class_final_back.service;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LessonService {
  private final LessonRepository lessonRepository;
  private final CategoryRepository categoryRepository;

  // 클래스 생성하기
  @Transactional
  public LessonSaveRespDto saveLesson(LessonSaveReqDto lessonSaveReqDto) throws IOException {
    String photoPath = DecodeUtil.saveDecodingImage(lessonSaveReqDto.getPhoto());
    lessonSaveReqDto.setPhoto(photoPath);
    Category categoryPS = categoryRepository.findById(lessonSaveReqDto.getCategoryId())
        .orElseThrow(() -> new CustomApiException("해당하지 않는 카테고리입니다.", HttpStatus.BAD_REQUEST));
    Lesson lessonPS = lessonRepository.save(lessonSaveReqDto.toEntity(categoryPS));
    return new LessonSaveRespDto(lessonPS);
  }

  // 클래스 수정하기

  // 클래스 삭제하기
}
