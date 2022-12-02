package site.hobbyup.class_final_back.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonBudgetListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonCategoryListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;
import site.hobbyup.class_final_back.service.LessonService;

@RequiredArgsConstructor
@RestController
public class LessonApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LessonService lessonService;

    // lesson 등록
    @PostMapping("/api/lesson")
    public ResponseEntity<?> saveLesson(@RequestBody LessonSaveReqDto lessonSaveReqDto,
            @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        log.debug("디버그 : LessonApiController-saveLesson 실행됨");
        LessonSaveRespDto lessonSaveRespDto = lessonService.saveLesson(lessonSaveReqDto, loginUser);
        return new ResponseEntity<>(new ResponseDto<>("클래스 생성 성공", lessonSaveRespDto), HttpStatus.CREATED);
    }

    // lesson 리스트 보기
    @GetMapping("/api/category/{categoryId}")
    public ResponseEntity<?> getLessonCategoryList(@PathVariable Long categoryId) {
        LessonCategoryListRespDto lessonCategoryListRespDto = lessonService.getLessonCategoryList(categoryId);
        return new ResponseEntity<>(new ResponseDto<>("클래스 리스트 불러오기 성공", lessonCategoryListRespDto),
                HttpStatus.OK);
    }

    // 예산별 필터링 리스트 테스트
    @GetMapping("/api/category/{categoryId}/value")
    public ResponseEntity<?> getLessonBudgetList(@PathVariable Long categoryId,
            @RequestParam(name = "min_price") Long minPrice, @RequestParam(name = "max_price") Long maxPrice) {
        LessonBudgetListRespDto LessonBudgetListRespDto = lessonService.getLessonBudgetList(categoryId, minPrice,
                maxPrice);
        return new ResponseEntity<>(new ResponseDto<>("클래스 리스트 필터링 성공", LessonBudgetListRespDto),
                HttpStatus.OK);
    }
}
