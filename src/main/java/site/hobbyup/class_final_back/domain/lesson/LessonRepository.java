package site.hobbyup.class_final_back.domain.lesson;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.hobbyup.class_final_back.dto.lesson.LessonCommonListDto;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

        // @Query("select l from Lesson l join fetch l.category c where l.category.id =
        // :categoryId and (l.price > :minPrice and l.price < :maxPrice)")
        // List<Lesson> findByCategory(@Param("categoryId") Long categoryId,
        // @Param("minPrice") Long minPrice,
        // @Param("maxPrice") Long maxPrice);

        @Query("select l from Lesson l where l.name LIKE %:keyword%")
        List<Lesson> findAllByKeyword(@Param("keyword") String keyword);

        @Query("select l from Lesson l join fetch l.expert u where l.expert.user.id = :userId")
        List<Lesson> findByUserId(@Param("userId") Long userId);

        // @Query(value = "SELECT * from lesson l ORDER BY created_at desc LIMIT 12",
        // nativeQuery = true)
        // List<Lesson> findAllLatest();

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

        // @Query(value = "SELECT l.photo as lessonPhoto, l.name AS lessonName, l.price
        // AS lessonPrice, (case when r.count IS NULL then 0 ELSE r.count END) AS
        // totalReview, (case when r.grade IS NULL then 0 ELSE r.grade END) AS avgGrade,
        // (case when s1.lesson_id IS NOT NULL then true ELSE false END) AS subscribed"
        // + " FROM lesson l LEFT OUTER JOIN (SELECT AVG(grade) AS grade, COUNT(*) AS
        // count, lesson_id FROM review GROUP BY lesson_id) r"
        // + " ON l.id = r.lesson_id"
        // + " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id = :userId)
        // s1"
        // + " ON l.id = s1.lesson_id"
        // + " LEFT OUTER JOIN (SELECT lesson_id, count(*) as sub_count FROM subscribe
        // group by lesson_id) s2"
        // + " ON l.id = s2.lesson_id"
        // + " ORDER BY s2.sub_count DESC", nativeQuery = true)
        // List<LessonSubscribeListDto> findAllBySubscribe(@Param("userId") Long
        // userId);

        // @Query(value = "SELECT l.photo as lessonPhoto, l.name AS lessonName, l.price
        // AS lessonPrice, (case when r.count IS NULL then 0 ELSE r.count END) AS
        // totalReview, (case when r.grade IS NULL then 0 ELSE r.grade END) AS avgGrade,
        // (case when s1.lesson_id IS NOT NULL then true ELSE false END) AS subscribed"
        // + " FROM lesson l LEFT OUTER JOIN (SELECT AVG(grade) AS grade, COUNT(*) AS
        // count, lesson_id FROM review GROUP BY lesson_id) r"
        // + " ON l.id = r.lesson_id"
        // + " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id is null)
        // s1"
        // + " ON l.id = s1.lesson_id"
        // + " LEFT OUTER JOIN (SELECT lesson_id, count(*) as sub_count FROM subscribe
        // group by lesson_id) s2"
        // + " ON l.id = s2.lesson_id"
        // + " ORDER BY s2.sub_count DESC", nativeQuery = true)
        // List<LessonSubscribeListDto> findAllBySubscribeNotLogin();

        // @Query(value = "select l.name as lessonName,"
        // + " l.price as lessonPrice,"
        // + " COUNT(r.id) AS totalReviews,"
        // + " (case when AVG(r.grade) IS null then 0.0 ELSE AVG(r.grade) END) AS
        // avgGrade,"
        // + " (case when s.lesson_id IS NOT NULL then true ELSE false END) AS
        // subscribed,"
        // + " (case when i.category_id IS NOT NULL then true ELSE false END) AS
        // recommand,"
        // + " (case when s2.count IS null then 0 ELSE s2.count END) AS ranking,"
        // + " l.created_at AS recent"
        // + " FROM lesson l LEFT OUTER JOIN review r ON l.id = r.lesson_id"
        // + " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id = :userId)
        // s"
        // + " ON l.id = s.lesson_id"
        // + " LEFT OUTER JOIN (SELECT category_id FROM interest WHERE user_id =
        // :userId) i"
        // + " ON l.category_id = i.category_id"
        // + " LEFT OUTER JOIN (SELECT COUNT(*) count, lesson_id FROM subscribe GROUP BY
        // lesson_id) s2"
        // + " ON l.id = s2.lesson_id"
        // + " WHERE l.category_id = :categoryId"
        // + " GROUP BY l.id"
        // + " ORDER BY recommand DESC", nativeQuery = true)
        // List<LessonSortListRespDto> findAllByRecommand(@Param("userId") Long userId,
        // @Param("categoryId") Long categoryId);

        // @Query(value = "select l.name as lessonName,"
        // + " l.price as lessonPrice,"
        // + " COUNT(r.id) AS totalReviews,"
        // + " (case when AVG(r.grade) IS null then 0.0 ELSE AVG(r.grade) END) AS
        // avgGrade,"
        // + " (case when s.lesson_id IS NOT NULL then true ELSE false END) AS
        // subscribed,"
        // + " (case when i.category_id IS NOT NULL then true ELSE false END) AS
        // recommand,"
        // + " (case when s2.count IS null then 0 ELSE s2.count END) AS ranking,"
        // + " l.created_at AS recent"
        // + " FROM lesson l LEFT OUTER JOIN review r ON l.id = r.lesson_id"
        // + " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id = :userId)
        // s"
        // + " ON l.id = s.lesson_id"
        // + " LEFT OUTER JOIN (SELECT category_id FROM interest WHERE user_id =
        // :userId) i"
        // + " ON l.category_id = i.category_id"
        // + " LEFT OUTER JOIN (SELECT COUNT(*) count, lesson_id FROM subscribe GROUP BY
        // lesson_id) s2"
        // + " ON l.id = s2.lesson_id"
        // + " WHERE l.category_id = :categoryId"
        // + " GROUP BY l.id"
        // + " ORDER BY ranking DESC", nativeQuery = true)
        // List<LessonSortListRespDto> findAllByRanking(@Param("userId") Long userId,
        // @Param("categoryId") Long categoryId);

        // @Query(value = "select l.name as lessonName,"
        // + " l.price as lessonPrice,"
        // + " COUNT(r.id) AS totalReviews,"
        // + " (case when AVG(r.grade) IS null then 0.0 ELSE AVG(r.grade) END) AS
        // avgGrade,"
        // + " (case when s.lesson_id IS NOT NULL then true ELSE false END) AS
        // subscribed,"
        // + " (case when i.category_id IS NOT NULL then true ELSE false END) AS
        // recommand,"
        // + " (case when s2.count IS null then 0 ELSE s2.count END) AS ranking,"
        // + " l.created_at AS recent"
        // + " FROM lesson l LEFT OUTER JOIN review r ON l.id = r.lesson_id"
        // + " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id = :userId)
        // s"
        // + " ON l.id = s.lesson_id"
        // + " LEFT OUTER JOIN (SELECT category_id FROM interest WHERE user_id =
        // :userId) i"
        // + " ON l.category_id = i.category_id"
        // + " LEFT OUTER JOIN (SELECT COUNT(*) count, lesson_id FROM subscribe GROUP BY
        // lesson_id) s2"
        // + " ON l.id = s2.lesson_id"
        // + " WHERE l.category_id = :categoryId"
        // + " GROUP BY l.id"
        // + " ORDER BY recent DESC", nativeQuery = true)
        // List<LessonSortListRespDto> findAllByRecent(@Param("userId") Long userId,
        // @Param("categoryId") Long categoryId);

}