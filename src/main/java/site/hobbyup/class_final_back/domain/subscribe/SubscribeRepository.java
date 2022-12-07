package site.hobbyup.class_final_back.domain.subscribe;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Query("select s from Subscribe s join fetch s.user u where s.user.id = :userId")
    List<Subscribe> findAllByUserId(@Param("userId") Long userId);

    @Query("select s from Subscribe s join fetch s.lesson l join fetch s.user u where s.user.id = :userId and s.lesson.id = :lessonId")
    Optional<Subscribe> findByUserIdAndLessonId(@Param("userId") Long userId, @Param("lessonId") Long lessonId);

}
