package site.hobbyup.class_final_back.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;
import site.hobbyup.class_final_back.service.LessonService;

@RequiredArgsConstructor
@RestController
public class LessonApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LessonService lessonService;

    @PostMapping("/api/lesson")
    public ResponseEntity<?> saveLesson(@RequestBody LessonSaveReqDto lessonSaveReqDto,
            @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        log.debug("디버그 : LessonApiController-saveLesson 실행됨");
        LessonSaveRespDto lessonSaveRespDto = lessonService.saveLesson(lessonSaveReqDto);
        return new ResponseEntity<>(new ResponseDto<>("클래스 생성 성공", lessonSaveRespDto), HttpStatus.CREATED);
    }

}
