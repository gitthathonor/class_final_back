package site.hobbyup.class_final_back.domain.interest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterestRepository extends JpaRepository<Interest, Long> {

  @Query("select i from Interest i where i.userId = :userId, nativeQuery = true")
  List<Interest> findAllByUserId(@Param("userId") Long userId);

}
