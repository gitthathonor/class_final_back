package site.hobbyup.class_final_back.domain.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.lesson.id = :lessonId order by r.createdAt desc")
    List<Review> findAllByLessonId(@Param("lessonId") Long lessonId);

    // @Query(value = "select count(*) from Review r fetch join r.lesson l where
    // r.lesson.id=:lessonId", nativeQuery = true)
    // Long findAllCount(@Param("lessonId") Long lessonId);

    // @Query(value = "select avg(r.grade) from Review r fetch join r.lesson l where
    // r.lesson.id = lessonId", nativeQuery = true)
    // Long findAvgGrade(@Param("lessonId") Long lessonId);
}
