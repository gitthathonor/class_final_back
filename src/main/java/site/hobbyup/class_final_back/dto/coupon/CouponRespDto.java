package site.hobbyup.class_final_back.dto.coupon;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.coupon.Coupon;

public class CouponRespDto {

    public static class CouponListRespDto {
        private List<CouponDto> couponList = new ArrayList<>();

        public CouponListRespDto(List<Coupon> couponList) {
            this.couponList = couponList.stream().map((coupon) -> new CouponDto(coupon)).collect(Collectors.toList());
        }

        @Setter
        @Getter
        public static class CouponDto {
            private Long id;
            private String title;
            private Long price;
            private Timestamp expiredDate;

            public CouponDto(Coupon coupon) {
                this.id = coupon.getId();
                this.title = coupon.getTitle();
                this.price = coupon.getPrice();
            }
        }
    }
}