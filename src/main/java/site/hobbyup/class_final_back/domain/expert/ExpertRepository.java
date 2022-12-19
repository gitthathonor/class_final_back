package site.hobbyup.class_final_back.domain.expert;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpertRepository extends JpaRepository<Expert, Long> {

    @Query("select e from Expert e where e.user.id = :userId")
    Optional<Expert> findByUserId(@Param("userId") Long userId);

}
