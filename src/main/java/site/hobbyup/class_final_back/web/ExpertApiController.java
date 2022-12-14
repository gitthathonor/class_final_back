package site.hobbyup.class_final_back.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.expert.ExpertRespDto.ExpertPageRespDto;
import site.hobbyup.class_final_back.service.ExpertService;

@RequiredArgsConstructor
@RestController
public class ExpertApiController {
    private final ExpertService expertService;

    // 전문가 마이 페이지 보기
    @GetMapping("/api/expert/{userId}/mypage")
    public ResponseEntity<?> getExpertPage(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        if (userId != loginUser.getUser().getId()) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        ExpertPageRespDto expertPageRespDto = expertService.getExpertPage(userId);
        return new ResponseEntity<>(new ResponseDto<>("전문가 페이지 보기 성공", expertPageRespDto), HttpStatus.OK);
    }
}
