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
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonReviewDto;
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

        // ?????? role ???????????? expert??? is_approval??? true??? ???????????? ???????????? ????????? ??? ?????? ?????? ??????.
        // lesson ??????
        @PostMapping("/api/lesson")
        public ResponseEntity<?> saveLesson(@RequestBody LessonSaveReqDto lessonSaveReqDto,
                        @AuthenticationPrincipal LoginUser loginUser) throws IOException {
                log.debug("????????? : LessonApiController-saveLesson ?????????");
                LessonSaveRespDto lessonSaveRespDto = lessonService.saveLesson(lessonSaveReqDto, loginUser);
                return new ResponseEntity<>(new ResponseDto<>("????????? ?????? ??????", lessonSaveRespDto), HttpStatus.CREATED);
        }

        // lesson ????????????
        @GetMapping("/api/category/lesson/{id}")
        public ResponseEntity<?> getLessonDetail(@PathVariable Long id,
                        @AuthenticationPrincipal LoginUser loginUser) {
                if (loginUser == null) {
                        LessonDetailRespDto lessonDetailRespDto = lessonService.getLessonDetailNotLogin(id);
                        return new ResponseEntity<>(new ResponseDto<>("????????? ???????????? ??????", lessonDetailRespDto),
                                        HttpStatus.OK);
                }
                LessonDetailRespDto lessonDetailRespDto = lessonService.getLessonDetail(id,
                                loginUser.getUser().getId());
                return new ResponseEntity<>(new ResponseDto<>("????????? ???????????? ??????", lessonDetailRespDto), HttpStatus.OK);
        }

        // ?????? ?????????
        @GetMapping("/api/main")
        public ResponseEntity<?> getLessonCommonList(@AuthenticationPrincipal LoginUser loginUser) {
                if (loginUser == null) {
                        List<LessonCommonListDto> lessonCommonListDtoList = lessonService.getLessonCommonListNotLogin();
                        return new ResponseEntity<>(new ResponseDto<>("?????? ????????? ?????? ??????", lessonCommonListDtoList),
                                        HttpStatus.OK);
                }
                List<LessonCommonListDto> lessonCommonListDtoList = lessonService
                                .getLessonCommonList(loginUser.getUser().getId());
                return new ResponseEntity<>(new ResponseDto<>("?????? ????????? ?????? ??????", lessonCommonListDtoList), HttpStatus.OK);
        }

        // ?????? ????????????
        @PutMapping("/api/lesson/{id}")
        public ResponseEntity<?> updateLesson(@RequestBody LessonUpdateReqDto lessonUpdateReqDto, @PathVariable Long id,
                        @AuthenticationPrincipal LoginUser loginUser) {
                log.debug("????????? : LessonApiController-updateLesson ?????????");
                LessonUpdateRespDto lessonUpdateRespDto = lessonService.updateLesson(lessonUpdateReqDto, id,
                                loginUser.getUser().getId());
                return new ResponseEntity<>(new ResponseDto<>(" ?????? ????????????", lessonUpdateRespDto), HttpStatus.OK);
        }

        // ???????????? ??? ?????? ????????? ?????? ?????? ?????? ????????? ??? ???????????? ??? ?????? ????????????
        @GetMapping("/api/category/{categoryId}")
        public ResponseEntity<?> getLessonCategoryList(@AuthenticationPrincipal LoginUser loginUser,
                        @PathVariable Long categoryId,
                        @RequestParam(name = "sort", required = false, defaultValue = "recent") String sort,
                        @RequestParam(name = "min_price", required = false, defaultValue = "0") Long minPrice,
                        @RequestParam(name = "max_price", required = false, defaultValue = "0") Long maxPrice) {
                if (loginUser == null) {
                        log.debug("????????? : LessonApiController - ???????????? ??? ??????????????? ????????? ??????");
                        List<LessonCategoryListRespDto> lessonCategoryListRespDtoList = lessonService
                                        .getLessonCategoryListNotLogin(categoryId, sort,
                                                        minPrice,
                                                        maxPrice);
                        return new ResponseEntity<>(new ResponseDto<>("???????????????, ???????????? ?????? ??????",
                                        lessonCategoryListRespDtoList),
                                        HttpStatus.OK);
                }
                log.debug("????????? : LessonApiController - getAllLessonList??????");
                List<LessonCategoryListRespDto> lessonCategoryListRespDtoList = lessonService
                                .getLessonCategoryList(loginUser.getUser().getId(), categoryId, sort, minPrice,
                                                maxPrice);
                return new ResponseEntity<>(new ResponseDto<>("????????? ???, ???????????? ?????? ??????",
                                lessonCategoryListRespDtoList),
                                HttpStatus.OK);
        }

        // ????????????
        @GetMapping("/api/lesson/search")
        public ResponseEntity<?> getLessonListBySearch(@AuthenticationPrincipal LoginUser loginUser,
                        @RequestParam(name = "keyword") String keyword) {
                log.debug("????????? : LessonApiController - getLessonListBySearch??????");
                if (loginUser == null) {
                        log.debug("????????? : LessonApiController - loginUser??? null");
                        List<LessonSearchListRespDto> lessonSearchListRespDtoList = lessonService
                                        .getLessonListBySearchNotLogin(keyword);
                        return new ResponseEntity<>(new ResponseDto<>("???????????? ???, ?????? ??????",
                                        lessonSearchListRespDtoList),
                                        HttpStatus.OK);
                }
                List<LessonSearchListRespDto> lessonSearchListRespDtoList = lessonService
                                .getLessonListBySearch(loginUser.getUser().getId(), keyword);
                return new ResponseEntity<>(new ResponseDto<>("????????? ???, ?????? ??????", lessonSearchListRespDtoList),
                                HttpStatus.OK);
        }

        // ?????? ?????? ????????????
        @GetMapping("/api/user/{userId}/subscribe")
        public ResponseEntity<?> getLessonSubscribedList(@AuthenticationPrincipal LoginUser loginUser) {
                List<LessonSubscribedListRespDto> lessonSubscribedListRespDtoList = lessonService
                                .getLessonSubscribedList(loginUser.getUser().getId());
                return new ResponseEntity<>(new ResponseDto<>("?????? ?????? ?????? ?????? ??????", lessonSubscribedListRespDtoList),
                                HttpStatus.OK);
        }

        // ???????????? ???????????? ?????? ????????? ??????
        @GetMapping("/api/expert/{userId}/sellingList")
        public ResponseEntity<?> getSellingLessonList(@PathVariable Long userId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                log.debug("????????? : LessonApiController - getSellingLessonList??????");
                if (userId != loginUser.getUser().getId()) {
                        throw new CustomApiException("????????? ????????????.", HttpStatus.FORBIDDEN);
                }
                LessonSellingByExpertRespDto lessonSellingByExpertRespDto = lessonService.getSellingLessonList(userId);
                return new ResponseEntity<>(new ResponseDto<>("???????????? ?????? ????????? ??????", lessonSellingByExpertRespDto),
                                HttpStatus.OK);
        }

        // ??????????????? ???????????? ?????? ????????? ??????
        @GetMapping("/api/user/{userId}/buyingList")
        public ResponseEntity<?> getBuyingLessonList(@PathVariable Long userId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                log.debug("????????? : LessonApiController - getBuyingLessonList??????");
                if (userId != loginUser.getUser().getId()) {
                        throw new CustomApiException("????????? ????????????.", HttpStatus.FORBIDDEN);
                }
                List<LessonBuyingByUserRespDto> lessonBuyingByUserRespDtoList = lessonService
                                .getBuyingLessonList(userId);
                return new ResponseEntity<>(new ResponseDto<>("???????????? ?????? ????????? ??????", lessonBuyingByUserRespDtoList),
                                HttpStatus.OK);
        }

        // ?????? ?????? ????????? ??????
        @GetMapping("/api/user/{userId}/buyingList/{lessonId}")
        public ResponseEntity<?> getLessonForReview(@PathVariable("userId") Long userId,
                        @PathVariable("lessonId") Long lessonId, @AuthenticationPrincipal LoginUser loginUser) {
                if (userId != loginUser.getUser().getId()) {
                        throw new CustomApiException("????????? ????????????.", HttpStatus.FORBIDDEN);
                }
                LessonReviewDto lessonReviewDto = lessonService.getLessonForReview(lessonId);
                return new ResponseEntity<>(new ResponseDto<>("?????? ?????? ????????? ?????? ??????", lessonReviewDto),
                                HttpStatus.OK);
        }

}
