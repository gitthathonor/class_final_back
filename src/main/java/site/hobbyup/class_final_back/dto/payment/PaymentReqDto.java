package site.hobbyup.class_final_back.dto.payment;

public class PaymentReqDto {
  public static class PaymentSaveReqDto {
    private Long totalPrice;
    private Long discountPrice;
    private Long finalPrice;
    private Long paymentTypeId;
    private Long userId;
    private Long lessonId;
  }
}
