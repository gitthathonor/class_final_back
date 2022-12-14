package site.hobbyup.class_final_back.domain.lesson;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.hobbyup.class_final_back.dto.lesson.LessonCommonListDto;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

        @Query("select l from Lesson l join fetch l.expert u where l.expert.user.id = :userId")
        List<Lesson> findByUserId(@Param("userId") Long userId);

        @Query(value = "SELECT l.id as lessonId, l.photo as lessonPhoto, l.name AS lessonName, l.price AS lessonPrice, (case when r.count IS NULL then 0 ELSE r.count END) AS totalReview, (case when r.grade IS NULL then 0 ELSE r.grade END) AS avgGrade, (case when s.lesson_id IS NOT NULL then true ELSE false END) AS subscribed"
                        + " FROM lesson l LEFT OUTER JOIN (SELECT AVG(grade) AS grade, COUNT(*) AS count, lesson_id FROM review GROUP BY lesson_id) r"
                        + " ON l.id = r.lesson_id"
                        + " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id = :userId) s"
                        + " ON l.id = s.lesson_id"
                        + " ORDER BY l.created_at DESC LIMIT 12", nativeQuery = true)
        List<LessonCommonListDto> findAllWithReview(@Param("userId") Long userId);

        @Query(value = "SELECT l.id as lessonId, l.photo as lessonPhoto, l.name AS lessonName, l.price AS lessonPrice, (case when r.count IS NULL then 0 ELSE r.count END) AS totalReview, (case when r.grade IS NULL then 0 ELSE r.grade END) AS avgGrade, (case when s.lesson_id IS NOT NULL then true ELSE false END) AS subscribed"
                        + " FROM lesson l LEFT OUTER JOIN (SELECT AVG(grade) AS grade, COUNT(*) AS count, lesson_id FROM review GROUP BY lesson_id) r"
                        + " ON l.id = r.lesson_id"
                        + " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id is null) s"
                        + " ON l.id = s.lesson_id"
                        + " ORDER BY l.created_at DESC LIMIT 12", nativeQuery = true)
        List<LessonCommonListDto> findAllWithReviewNotLogin();

}