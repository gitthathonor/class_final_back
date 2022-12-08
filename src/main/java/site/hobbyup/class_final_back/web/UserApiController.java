package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.user.UserReqDto.JoinReqDto;
import site.hobbyup.class_final_back.dto.user.UserReqDto.UserUpdateReqDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.JoinRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.MyLessonListRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.MyPageRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.UserDeleteRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.UserUpdateRespDto;
import site.hobbyup.class_final_back.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @PostMapping("/api/join")
    public ResponseEntity<?> join(@RequestBody JoinReqDto joinReqDto) {
        log.debug("디버그 : UserApiController-join 실행됨");
        JoinRespDto joinRespDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>("회원가입성공", joinRespDto), HttpStatus.CREATED);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateReqDto userUpdateReqDto, @PathVariable Long id) {
        log.debug("디버그 : UserApiController-updateUser 실행됨");
        UserUpdateRespDto userUpdateRespDto = userService.updateUser(userUpdateReqDto, id);
        return new ResponseEntity<>(new ResponseDto<>("회원정보 수정완료", userUpdateRespDto), HttpStatus.OK);
    }

    @GetMapping("/api/user/session")
    public String getSessionUser(@AuthenticationPrincipal LoginUser loginUser) {
        return "role : " + loginUser.getUser().getRole();
    }

    @PutMapping("/api/user/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.debug("디버그 : UserApiController-deleteUser 실행됨");
        UserDeleteRespDto userDeleteRespDto = userService.deleteUser(id);
        return new ResponseEntity<>(new ResponseDto<>("회원 탈퇴 완료", userDeleteRespDto), HttpStatus.OK);
    }

    @GetMapping("/api/user/{userId}/mypage")
    public ResponseEntity<?> getMyPage(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : UserApiController-getMyPage 실행됨");
        if (userId != loginUser.getUser().getId()) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        MyPageRespDto myPageRespDto = userService.getMyPage(userId);
        return new ResponseEntity<>(new ResponseDto<>("마이페이지 보기", myPageRespDto), HttpStatus.OK);
    }

    @GetMapping("/api/user/{userId}/mypage/lesson")
    public ResponseEntity<?> getMyLesson(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : UserApiController-getMyLesson 실행됨");
        if (userId != loginUser.getUser().getId()) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        MyLessonListRespDto myLessonRespDto = userService.getMyLesson(userId);
        return new ResponseEntity<>(new ResponseDto<>("마이페이지 클래스 보기", myLessonRespDto), HttpStatus.OK);
    }
}
