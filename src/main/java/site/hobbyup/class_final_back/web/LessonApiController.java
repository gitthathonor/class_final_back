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
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonCategoryListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonDetailRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;
import site.hobbyup.class_final_back.service.LessonService;

@RequiredArgsConstructor
@RestController
public class LessonApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LessonService lessonService;

    // 추후 role 체크해서 master인 사람들만 클래스를 생성할 수 있게 해야 한다.
    // lesson 등록
    @PostMapping("/api/lesson")
    public ResponseEntity<?> saveLesson(@RequestBody LessonSaveReqDto lessonSaveReqDto,
            @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        log.debug("디버그 : LessonApiController-saveLesson 실행됨");
        LessonSaveRespDto lessonSaveRespDto = lessonService.saveLesson(lessonSaveReqDto, loginUser);
        return new ResponseEntity<>(new ResponseDto<>("클래스 생성 성공", lessonSaveRespDto), HttpStatus.CREATED);
    }

    // lesson 리스트 보기(쿼리스트링으로 예산별 필터까지 적용)
    @GetMapping("/api/category/{categoryId}")
    public ResponseEntity<?> getLessonCategoryList(@PathVariable Long categoryId,
            @RequestParam(name = "min_price") Long minPrice, @RequestParam(name = "max_price") Long maxPrice) {
        LessonCategoryListRespDto lessonCategoryListRespDto = lessonService.getLessonCategoryList(categoryId, minPrice,
                maxPrice);
        return new ResponseEntity<>(new ResponseDto<>("클래스 리스트 불러오기 성공", lessonCategoryListRespDto),
                HttpStatus.OK);
    }

    // lesson 상세보기
    @GetMapping("/api/category/lesson/{lessonId}")
    public ResponseEntity<?> getLessonDetail(@PathVariable Long lessonId) {
        LessonDetailRespDto lessonDetailRespDto = lessonService.getLessonDetail(lessonId);
        return new ResponseEntity<>(new ResponseDto<>("클래스 상세보기 성공", lessonDetailRespDto), HttpStatus.OK);
    }

}
