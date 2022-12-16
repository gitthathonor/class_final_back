package site.hobbyup.class_final_back.dto.payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private String paymentDate;

    public PaymentSaveRespDto(Payment payment) {
      this.paymentId = payment.getId();
      this.totalPrice = payment.getTotalPrice();
      this.discountPrice = payment.getDiscountPrice();
      this.finalPrice = payment.getFinalPrice();
      this.totalCount = payment.getTotalCount();
      this.paymentTypeName = payment.getPaymentType().getName();
      this.lessonName = payment.getLesson().getName();
      this.username = payment.getUser().getUsername();
      this.paymentDate = payment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

  }

  @Setter
  @Getter
  public static class PaymentListRespDto {
    private List<PaymentDto> paymentDtoList = new ArrayList<>();

    public PaymentListRespDto(List<Payment> paymentList) {
      this.paymentDtoList = paymentList.stream().map((payment) -> new PaymentDto(payment)).collect(Collectors.toList());
    }

    @Setter
    @Getter
    public class PaymentDto {
      private String lessonName;
      private Long finalPrice;
      private String paymentType;
      private String paymentDate;

      public PaymentDto(Payment payment) {
        this.lessonName = payment.getLesson().getName();
        this.finalPrice = payment.getFinalPrice();
        this.paymentType = payment.getPaymentType().getName();
        this.paymentDate = payment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      }

    }

  }

  @Setter
  @Getter
  public static class PaymentForSellingRespDto {
    private List<PaymentDto> paymentDtoList = new ArrayList<>();

    public PaymentForSellingRespDto(List<Payment> paymentList) {
      this.paymentDtoList = paymentList.stream().map((payment) -> new PaymentDto(payment)).collect(Collectors.toList());
    }

    @Setter
    @Getter
    public class PaymentDto {
      private String lessonName;
      private Long finalPrice;
      private String paymentType;
      private String paymentDate;

      public PaymentDto(Payment payment) {
        this.lessonName = payment.getLesson().getName();
        this.finalPrice = payment.getFinalPrice();
        this.paymentType = payment.getPaymentType().getName();
        this.paymentDate = payment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      }

    }

  }
}
