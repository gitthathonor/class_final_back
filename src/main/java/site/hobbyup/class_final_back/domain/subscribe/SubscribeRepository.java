package site.hobbyup.class_final_back.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Query("select s from Subscribe s join fetch s.user u where s.user.id = :userId")
    Subscribe findByUserId(@Param("userId") Long userId);
}
