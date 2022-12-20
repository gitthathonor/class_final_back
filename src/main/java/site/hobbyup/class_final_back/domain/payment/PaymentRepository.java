package site.hobbyup.class_final_back.domain.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

  @Query("select p from Payment p where p.user.id = :userId order by p.createdAt DESC")
  List<Payment> findAllByUserId(@Param("userId") Long userId);

  @Query("select p from Payment p where p.user.id = :userId and p.lesson.id = :lessonId")
  Optional<Payment> findByUserIdAndLessonId(@Param("userId") Long userId, @Param("lessonId") Long lessonId);

  @Query("select p from Payment p where p.user.id = :userId")
  List<Payment> findAllLessonByUserId(@Param("userId") Long userId);
}
