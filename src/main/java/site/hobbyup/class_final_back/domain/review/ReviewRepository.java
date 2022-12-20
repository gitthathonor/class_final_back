package site.hobbyup.class_final_back.domain.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.lesson.id = :lessonId order by r.createdAt desc")
    List<Review> findAllByLessonId(@Param("lessonId") Long lessonId);

    @Query("select COUNT(r.id), AVG(r.grade) from Review r GROUP BY r.lesson.id")
    List<Review> findAvgAndTotalByLesson();

    // 별점평균, 리뷰갯수 연산데이터 쿼리(가게 목록보기에 사용)
    // @Query(value = "select count(cr.id) count, avg(cr.star_point) starPoint, s.id
    // storeId from customer_reviews cr left join stores s on cr.store_id =s.id
    // group by s.id", nativeQuery = true)
    // List<CustomerReviewInterface> findAllByStoreReviewToStarPoint();

    // @Query(value = "select count(*) from Review r fetch join r.lesson l where
    // r.lesson.id=:lessonId", nativeQuery = true)
    // Long findAllCount(@Param("lessonId") Long lessonId);

    // @Query(value = "select avg(r.grade) from Review r fetch join r.lesson l where
    // r.lesson.id = lessonId", nativeQuery = true)
    // Long findAvgGrade(@Param("lessonId") Long lessonId);
}
