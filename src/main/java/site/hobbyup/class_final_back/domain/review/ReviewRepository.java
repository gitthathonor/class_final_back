package site.hobbyup.class_final_back.domain.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.lesson.id = :lessonId")
    List<Review> findAllByLessonId(@Param("lessonId") Long lessonId);

    @Query(value = "select count(*) from review r", nativeQuery = true)
    Long findAllCount();

}
