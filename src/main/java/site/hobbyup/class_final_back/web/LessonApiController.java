package site.hobbyup.class_final_back.web;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpsRedirectSpec;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.lesson.LessonCommonListDto;
import site.hobbyup.class_final_back.dto.lesson.LessonSubscribeListDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonUpdateReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonCategoryListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonDetailRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonLatestListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonUpdateRespDto;
import site.hobbyup.class_final_back.service.LessonService;

@RequiredArgsConstructor
@RestController
public class LessonApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LessonService lessonService;

    // 추후 role 체크해서 expert의 is_approval이 true인 사람들만 클래스를 생성할 수 있게 해야 한다.
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
    public ResponseEntity<?> getLessonDetail(@PathVariable Long lessonId,
            @AuthenticationPrincipal LoginUser loginUser) {
        if (loginUser == null) {
            LessonDetailRespDto lessonDetailRespDto = lessonService.getLessonDetailNotLogin(lessonId);
            return new ResponseEntity<>(new ResponseDto<>("클래스 상세보기 성공", lessonDetailRespDto), HttpStatus.OK);
        }
        LessonDetailRespDto lessonDetailRespDto = lessonService.getLessonDetail(lessonId, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("클래스 상세보기 성공", lessonDetailRespDto), HttpStatus.OK);
    }

    // 최신순 정렬
    @GetMapping("/api/lesson/latest")
    public ResponseEntity<?> getLatestLessonList() {
        LessonLatestListRespDto lessonLatestListRespDto = lessonService.getLatestLessonList();
        return new ResponseEntity<>(new ResponseDto<>("클래스 최신순으로 정렬", lessonLatestListRespDto), HttpStatus.OK);
    }

    // 메인 페이지
    @GetMapping("/api/main")
    public ResponseEntity<?> getLessonCommonList(@AuthenticationPrincipal LoginUser loginUser) {
        if (loginUser == null) {
            List<LessonCommonListDto> lessonCommonListDtos = lessonService.getLessonCommonListNotLogin();
            return new ResponseEntity<>(new ResponseDto<>("클래스 상세보기 성공", lessonCommonListDtos), HttpStatus.OK);
        }
        List<LessonCommonListDto> lessonCommonListDtos = lessonService.getLessonCommonList(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("테스트", lessonCommonListDtos), HttpStatus.OK);
    }

    // 레슨 수정하기
    @PutMapping("/api/lesson/{id}")
    public ResponseEntity<?> updateLesson(@RequestBody LessonUpdateReqDto lessonUpdateReqDto, @PathVariable Long id,
            @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : LessonApiController-updateLesson 실행됨");
        LessonUpdateRespDto lessonUpdateRespDto = lessonService.updateLesson(lessonUpdateReqDto, id,
                loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(" 레슨 수정완료", lessonUpdateRespDto), HttpStatus.OK);
    }

    // 구독순 정렬
    @GetMapping("/api/lesson/subscribe")
    public ResponseEntity<?> getLessonSubscribeList(@AuthenticationPrincipal LoginUser loginUser) {
        if (loginUser == null) {
            List<LessonSubscribeListDto> lessonSubscribeListDtos = lessonService.getLessonSubscribeListNotLogin();
            return new ResponseEntity<>(new ResponseDto<>("클래스 구독순으로 정렬", lessonSubscribeListDtos), HttpStatus.OK);
        }
        List<LessonSubscribeListDto> lessonSubscribeListDtos = lessonService
                .getLessonSubscribeList(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("클래스 구독순으로 정렬", lessonSubscribeListDtos), HttpStatus.OK);
    }

    // @GetMapping("/api/category/")
    // public ResponseEntity<?> getLessonListByRecommand() {

    // }

}
