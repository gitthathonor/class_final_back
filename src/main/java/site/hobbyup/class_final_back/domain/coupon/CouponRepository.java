package site.hobbyup.class_final_back.domain.coupon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c join fetch c.user u where c.user.id = :userId")
    List<Coupon> findAllByUserId(@Param("userId") Long userId);

}
