package site.hobbyup.class_final_back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.coupon.Coupon;
import site.hobbyup.class_final_back.domain.coupon.CouponRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.coupon.CouponRespDto.CouponListRespDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponService extends DecodeUtil {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public CouponListRespDto getCouponList(Long userId) {
        log.debug("디버그 : service - 쿠폰 리스트보기 시작");
        User userPS = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomApiException("존재하지 않는 유저입니다.", HttpStatus.FORBIDDEN));

        List<Coupon> couponList = couponRepository.findByUserId(userPS.getId());
        if (couponList.size() == 0) {
            throw new CustomApiException("쿠폰이 존재하지 않습니다.", HttpStatus.FORBIDDEN);
        }
        return new CouponListRespDto(couponList);
    }

}
