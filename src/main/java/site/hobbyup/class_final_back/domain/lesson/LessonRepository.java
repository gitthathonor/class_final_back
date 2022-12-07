package site.hobbyup.class_final_back.domain.lesson;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository.LessonLatestListRespDto.LessonLatestRespDto;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

        @Query("select l from Lesson l join fetch l.category c where l.category.id = :categoryId and (l.price > :minPrice and l.price < :maxPrice)")
        List<Lesson> findByCategory(@Param("categoryId") Long categoryId, @Param("minPrice") Long minPrice,
                        @Param("maxPrice") Long maxPrice);

        // where절에 categoryId가 들어와서 걸린다.
        @Query(value = "select l.title lessonName, l.price lessonPrice, s2.totalReview lessonReviewsCount, " +
                        "s2.totalGrade lessonAvgGrade, s2.isSubscribed isSubscribed" +
                        "from lesson l left outer join (select count(*) totalReview, AVG(grade) totalGrade, " +
                        "(select count(*) from subscribe s1 where s1.user_id = 1)>0 isSubscribed, r.lesson_id id" +
                        "from review r left outer join subscribe s on r.lesson_id = s.lesson_id where r.lesson_id = 1) s2"
                        +
                        "ON l.id = s2.id" +
                        "WHERE l.id = 1;", nativeQuery = true)
        List<Lesson> findAllLessonByCategoryId(@Param("categoryId") Long categoryId);

        @Query("select l from Lesson l where l.name LIKE %:keyword%")
        List<Lesson> findAllByKeyword(@Param("keyword") String keyword);

        @Query("select l from Lesson l join fetch l.user u where l.user.id = :userId")
        List<Lesson> findByUserId(@Param("userId") Long userId);

        // @Query(value = "SELECT * from lesson l ORDER BY created_at desc LIMIT 12",
        // nativeQuery = true)
        // List<Lesson> findAllLatest();

        @Query(value = "SELECT l.title, l.photo, l.price, s2.totalReview, s2.avgGrade from lesson l " +
                        "left outer join (select count(*) totalReview, AVG(grade) avgGrade, r.lesson_id " +
                        "from review r) s2 ON l.id = s2.id " +
                        "ORDER BY created_at desc LIMIT 12", nativeQuery = true)
        List<LessonLatestRespDto> findAllLatest();

        @Setter
        @Getter
        @Builder
        public static class LessonLatestListRespDto {
                // List<LessonDto> lessonList = new ArrayList<>();
                // private LessonLatestRespDto lessonLatestRespDto;

                // public LessonLatestListRespDto(List<LessonDto> lessonList,
                // LessonLatestRespDto lessonLatestRespDto) {
                // this.lessonList = lessonList;
                // this.lessonLatestRespDto = lessonLatestRespDto;
                // }

                // public LessonLatestListRespDto(List<Lesson> lessonList, ReviewDto reviewDto)
                // {

                // this.lessonList = lessonList.stream().map(
                // (lesson) -> new LessonDto(lesson))
                // .collect(Collectors.toList());
                // }
                // }
                private String name;
                private Long price;
                private String photo;
                private Long totalReview;
                private Long avgGrade;

                @Setter
                @Getter
                public static class LessonLatestRespDto {
                        private String name;
                        private Long price;
                        private String photo;
                        private Long totalReview;
                        private Long avgGrade;

                        public LessonLatestRespDto(Lesson lesson, ReviewDto reviewDto) {
                                this.name = lesson.getName();
                                this.price = lesson.getPrice();
                                this.photo = lesson.getPhoto();
                                this.totalReview = reviewDto.getTotalReview();
                                this.avgGrade = reviewDto.getAvgGrade();
                        }

                        @Setter
                        @Getter
                        public static class ReviewDto {
                                private Long totalReview;
                                private Long avgGrade;

                                public ReviewDto(Long totalReview, Long avgGrade) {
                                        this.totalReview = totalReview;
                                        this.avgGrade = avgGrade;
                                }
                        }
                }
        }
}