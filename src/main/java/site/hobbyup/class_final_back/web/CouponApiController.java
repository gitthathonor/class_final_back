package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import site.hobbyup.class_final_back.dto.coupon.CouponRespDto.CouponListRespDto;
import site.hobbyup.class_final_back.service.CouponService;

@RequiredArgsConstructor
@RestController
public class CouponApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CouponService couponService;

    @GetMapping("/api/user/{userId}/coupon")
    public ResponseEntity<?> getCouponList(@PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : controller - 쿠폰 리스트보기 시작");
        if (userId != loginUser.getUser().getId()) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        CouponListRespDto couponListRespDto = couponService.getCouponList(userId);
        return new ResponseEntity<>(new ResponseDto<>("쿠폰 리스트보기", couponListRespDto), HttpStatus.CREATED);
    }

}
