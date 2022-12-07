package site.hobbyup.class_final_back.dto.payment;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.payment.Payment;

public class PaymentRespDto {
  @Setter
  @Getter
  public static class PaymentSaveRespDto {
    private Long paymentId;
    private Long totalPrice;
    private Long discountPrice;
    private Long finalPrice;
    private Integer totalCount;
    private String paymentTypeName;
    private String lessonName;
    private String username;
    private LocalDateTime paymentDate;

    public PaymentSaveRespDto(Payment payment) {
      this.paymentId = payment.getId();
      this.totalPrice = payment.getTotalPrice();
      this.discountPrice = payment.getDiscountPrice();
      this.finalPrice = payment.getFinalPrice();
      this.totalCount = payment.getTotalCount();
      this.paymentTypeName = payment.getPaymentType().getName();
      this.lessonName = payment.getLesson().getName();
      this.username = payment.getUser().getUsername();
      this.paymentDate = payment.getCreatedAt();
    }

  }
}
