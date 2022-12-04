package site.hobbyup.class_final_back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonCategoryListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LessonService {
  private final LessonRepository lessonRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  // 클래스 생성하기
  @Transactional
  public LessonSaveRespDto saveLesson(LessonSaveReqDto lessonSaveReqDto, LoginUser loginUser) throws IOException {

    // 이미지 파일 디코딩 후, 경로값 가져오기
    String photoPath = DecodeUtil.saveDecodingImage(lessonSaveReqDto.getPhoto());
    lessonSaveReqDto.setPhoto(photoPath);

    // 입력받은 카테고리 정보를 db의 정보와 비교해서 카테고리값 가져오기
    Category categoryPS = categoryRepository.findById(lessonSaveReqDto.getCategoryId())
        .orElseThrow(() -> new CustomApiException("해당하지 않는 카테고리입니다.", HttpStatus.BAD_REQUEST));

    // jwt에 담긴 user의 정보를 통해서 db에서 user데이터를 영속화 한 후에 RequestDto에 저장
    User userPS = userRepository.findById(loginUser.getUser().getId())
        .orElseThrow(() -> new CustomApiException("회원가입이 되지 않은 유저입니다.", HttpStatus.BAD_REQUEST));

    // toEntity로 엔티티화 시킨 후에 저장하고 json(ResponseDto) 반환
    Lesson lessonPS = lessonRepository.save(lessonSaveReqDto.toEntity(categoryPS, userPS));
    return new LessonSaveRespDto(lessonPS);
  }

  // 클래스 리스트 보기(카테고리별 + 예산별 필터링 적용)
  public LessonCategoryListRespDto getLessonCategoryList(Long categoryId, Long minPrice, Long maxPrice) {

    // @PathVariable로 넘겨받은 categoryId를 통해서 카테고리를 영속화
    Category categoryPS = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CustomApiException("존재하지 않는 카테고리 입니다.", HttpStatus.BAD_REQUEST));

    // 영속화시킨 카테고리의 id로 where절을 걸어서 Lesson의 list를 반환
    List<Lesson> lessonListPS = lessonRepository.findByCategory(categoryPS.getId(), minPrice, maxPrice);

    // 영속화시킨 lesson의
    return new LessonCategoryListRespDto(categoryPS, lessonListPS);
  }

  public void getLessonDetail(Long id) {
    Lesson lessonPS = lessonRepository.findById(id)
        .orElseThrow(() -> new CustomApiException("해당 수업 없읍", HttpStatus.BAD_REQUEST));
  }

  // 클래스 상세보기

  // 클래스 수정하기

  // 클래스 삭제하기
}
