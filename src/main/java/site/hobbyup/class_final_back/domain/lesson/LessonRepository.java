package site.hobbyup.class_final_back.domain.lesson;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.hobbyup.class_final_back.dto.lesson.LessonCommonListDto;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

        @Query("select l from Lesson l join fetch l.category c where l.category.id = :categoryId and (l.price > :minPrice and l.price < :maxPrice)")
        List<Lesson> findByCategory(@Param("categoryId") Long categoryId, @Param("minPrice") Long minPrice,
                        @Param("maxPrice") Long maxPrice);

        // where절에 categoryId가 들어와서 걸린다.
        // @Query(value = "select l.title lessonName, l.price lessonPrice,
        // s2.totalReview lessonReviewsCount, " +
        // "s2.totalGrade lessonAvgGrade, s2.isSubscribed isSubscribed" +
        // "from lesson l left outer join (select count(*) totalReview, AVG(grade)
        // totalGrade, " +
        // "(select count(*) from subscribe s1 where s1.user_id = 1)>0 isSubscribed,
        // r.lesson_id id" +
        // "from review r left outer join subscribe s on r.lesson_id = s.lesson_id where
        // r.lesson_id = 1) s2"
        // +
        // "ON l.id = s2.id" +
        // "WHERE l.id = 1;", nativeQuery = true)
        // List<Lesson> findAllLessonByCategoryId(@Param("categoryId") Long categoryId);

        @Query("select l from Lesson l where l.name LIKE %:keyword%")
        List<Lesson> findAllByKeyword(@Param("keyword") String keyword);

        @Query("select l from Lesson l join fetch l.user u where l.user.id = :userId")
        List<Lesson> findByUserId(@Param("userId") Long userId);

        @Query(value = "SELECT * from lesson l ORDER BY created_at desc LIMIT 12", nativeQuery = true)
        List<Lesson> findAllLatest();

        @Query(value = "SELECT l.photo as lessonPhoto, l.name AS lessonName, l.price AS lessonPrice, (case when r.count IS NULL then 0 ELSE r.count END) AS totalReview, (case when r.grade IS NULL then 0 ELSE r.grade END) AS avgGrade, (case when s.lesson_id IS NOT NULL then true ELSE false END) AS subscribed"
                        + " FROM lesson l LEFT OUTER JOIN (SELECT AVG(grade) AS grade, COUNT(*) AS count, lesson_id FROM review GROUP BY lesson_id) r"
                        + " ON l.id = r.lesson_id"
                        + " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id = :userId) s"
                        + " ON l.id = s.lesson_id"
                        + " ORDER BY l.created_at DESC LIMIT 12", nativeQuery = true)
        List<LessonCommonListDto> findAllWithReview(@Param("userId") Long userId);
}