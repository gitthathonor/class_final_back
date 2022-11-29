package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.user.UserReqDto.JoinReqDto;
import site.hobbyup.class_final_back.dto.user.UserReqDto.UserUpdateReqDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.JoinRespDto;
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

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.debug("디버그 : UserApiController-deleteUser 실행됨");
        userService.deleteUser(id);
        return new ResponseEntity<>(new ResponseDto<>("회원 탈퇴 완료", null), HttpStatus.OK);
    }
}
