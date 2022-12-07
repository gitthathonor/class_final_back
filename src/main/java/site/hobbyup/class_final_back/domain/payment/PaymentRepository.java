package site.hobbyup.class_final_back.domain.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

  @Query("select p from Payment p where p.user.id = :userId")
  List<Payment> findAllByUserId(@Param("userId") Long userId);

}
