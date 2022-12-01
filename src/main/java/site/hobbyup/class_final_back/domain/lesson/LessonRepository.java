package site.hobbyup.class_final_back.domain.lesson;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

  @Query("select l from Lesson l join fetch l.category c where l.category.id = :categoryId")
  List<Lesson> findByCategory(@Param("categoryId") Long categoryId);

}
