package site.hobbyup.class_final_back.domain.review;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.lesson.id = :lessonId")
    List<Review> findAllByLessonId(@Param("lessonId") Long lessonId);

    // @Query(value = "select count(r) from Review r where r.lesson.id=:lessonid")
    // Long findAllCount(@Param("lessonId") Long lessonId);

    // @Query(value = "select avg(r.grade) from Review r join fetch Lesson l where
    // r.lesson.id = lessonId")
    // Long findAvgGrade(@Param("lessonId") Long lessonId);
}
