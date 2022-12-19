package site.hobbyup.class_final_back.web;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.lesson.LessonCommonListDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonUpdateReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonBuyingByUserRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonCategoryListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonDetailRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSearchListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSellingByExpertRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSubscribedListRespDto;
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

        // lesson 상세보기
        @GetMapping("/api/category/lesson/{id}")
        public ResponseEntity<?> getLessonDetail(@PathVariable Long id,
                        @AuthenticationPrincipal LoginUser loginUser) {
                if (loginUser == null) {
                        LessonDetailRespDto lessonDetailRespDto = lessonService.getLessonDetailNotLogin(id);
                        return new ResponseEntity<>(new ResponseDto<>("클래스 상세보기 성공", lessonDetailRespDto),
                                        HttpStatus.OK);
                }
                LessonDetailRespDto lessonDetailRespDto = lessonService.getLessonDetail(id,
                                loginUser.getUser().getId());
                return new ResponseEntity<>(new ResponseDto<>("클래스 상세보기 성공", lessonDetailRespDto), HttpStatus.OK);
        }

        // 메인 페이지
        @GetMapping("/api/main")
        public ResponseEntity<?> getLessonCommonList(@AuthenticationPrincipal LoginUser loginUser) {
                if (loginUser == null) {
                        List<LessonCommonListDto> lessonCommonListDtoList = lessonService.getLessonCommonListNotLogin();
                        return new ResponseEntity<>(new ResponseDto<>("메인 페이지 출력 성공", lessonCommonListDtoList),
                                        HttpStatus.OK);
                }
                List<LessonCommonListDto> lessonCommonListDtoList = lessonService
                                .getLessonCommonList(loginUser.getUser().getId());
                return new ResponseEntity<>(new ResponseDto<>("메인 페이지 출력 성공", lessonCommonListDtoList), HttpStatus.OK);
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

        // 카테고리 별 레슨 리스트 출력 시에 쿼리 스트링 및 정렬까지 한 번에 처리하기
        @GetMapping("/api/category/{categoryId}")
        public ResponseEntity<?> getLessonCategoryList(@AuthenticationPrincipal LoginUser loginUser,
                        @PathVariable Long categoryId,
                        @RequestParam(name = "sort", required = false, defaultValue = "recent") String sort,
                        @RequestParam(name = "min_price", required = false, defaultValue = "0") Long minPrice,
                        @RequestParam(name = "max_price", required = false, defaultValue = "0") Long maxPrice) {
                if (loginUser == null) {
                        log.debug("디버그 : LessonApiController - 비로그인 시 카테고리별 페이지 보기");
                        List<LessonCategoryListRespDto> lessonCategoryListRespDtoList = lessonService
                                        .getLessonCategoryListNotLogin(categoryId, sort,
                                                        minPrice,
                                                        maxPrice);
                        return new ResponseEntity<>(new ResponseDto<>("비로그인시, 카테고리 정렬 완료",
                                        lessonCategoryListRespDtoList),
                                        HttpStatus.OK);
                }
                log.debug("디버그 : LessonApiController - getAllLessonList실행");
                List<LessonCategoryListRespDto> lessonCategoryListRespDtoList = lessonService
                                .getLessonCategoryList(loginUser.getUser().getId(), categoryId, sort, minPrice,
                                                maxPrice);
                return new ResponseEntity<>(new ResponseDto<>("로그인 시, 카테고리 정렬 완료",
                                lessonCategoryListRespDtoList),
                                HttpStatus.OK);
        }

        // 검색하기
        @GetMapping("/api/lesson/search")
        public ResponseEntity<?> getLessonListBySearch(@AuthenticationPrincipal LoginUser loginUser,
                        @RequestParam(name = "keyword") String keyword) {
                log.debug("디버그 : LessonApiController - getLessonListBySearch실행");
                if (loginUser == null) {
                        log.debug("디버그 : LessonApiController - loginUser가 null");
                        List<LessonSearchListRespDto> lessonSearchListRespDtoList = lessonService
                                        .getLessonListBySearchNotLogin(keyword);
                        return new ResponseEntity<>(new ResponseDto<>("비로그인 시, 검색 성공",
                                        lessonSearchListRespDtoList),
                                        HttpStatus.OK);
                }
                List<LessonSearchListRespDto> lessonSearchListRespDtoList = lessonService
                                .getLessonListBySearch(loginUser.getUser().getId(), keyword);
                return new ResponseEntity<>(new ResponseDto<>("로그인 시, 검색 성공", lessonSearchListRespDtoList),
                                HttpStatus.OK);
        }

        // 찜한 레슨 목록보기
        @GetMapping("/api/user/{userId}/subscribe")
        public ResponseEntity<?> getLessonSubscribedList(@AuthenticationPrincipal LoginUser loginUser) {
                List<LessonSubscribedListRespDto> lessonSubscribedListRespDtoList = lessonService
                                .getLessonSubscribedList(loginUser.getUser().getId());
                return new ResponseEntity<>(new ResponseDto<>("찜한 레슨 목록 보기 성공", lessonSubscribedListRespDtoList),
                                HttpStatus.OK);
        }

        // 전문가가 판매중인 레슨 리스트 보기
        @GetMapping("/api/expert/{userId}/sellingList")
        public ResponseEntity<?> getSellingLessonList(@PathVariable Long userId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                log.debug("디버그 : LessonApiController - getSellingLessonList실행");
                if (userId != loginUser.getUser().getId()) {
                        throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
                }
                LessonSellingByExpertRespDto lessonSellingByExpertRespDto = lessonService.getSellingLessonList(userId);
                return new ResponseEntity<>(new ResponseDto<>("판매중인 레슨 리스트 보기", lessonSellingByExpertRespDto),
                                HttpStatus.OK);
        }

        // 일반회원이 수강중인 레슨 리스트 보기
        @GetMapping("/api/user/{userId}/buyingList")
        public ResponseEntity<?> getBuyingLessonList(@PathVariable Long userId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                log.debug("디버그 : LessonApiController - getBuyingLessonList실행");
                if (userId != loginUser.getUser().getId()) {
                        throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
                }
                List<LessonBuyingByUserRespDto> lessonBuyingByUserRespDtoList = lessonService
                                .getBuyingLessonList(userId);
                return new ResponseEntity<>(new ResponseDto<>("수강중인 레슨 리스트 보기", lessonBuyingByUserRespDtoList),
                                HttpStatus.OK);
        }
}
