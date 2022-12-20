package site.hobbyup.class_final_back.dto.payment;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.coupon.Coupon;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.payment.Payment;
import site.hobbyup.class_final_back.domain.paymentType.PaymentType;
import site.hobbyup.class_final_back.domain.user.User;

public class PaymentReqDto {
  @Setter
  @Getter
  public static class PaymentSaveReqDto {

    private Integer totalCount;
    private Long paymentTypeId;
    private String impId;

    public Payment toEntity(User user, Coupon coupon, Lesson lesson, PaymentType paymentType) {
      return Payment.builder()
          .totalPrice(lesson.getPrice())
          .discountPrice(coupon.getPrice())
          .finalPrice((lesson.getPrice() - coupon.getPrice()) * totalCount)
          .totalCount(totalCount)
          .paymentType(paymentType)
          .user(user)
          .lesson(lesson)
          .impId(impId)
          .build();
    }
  }
}
