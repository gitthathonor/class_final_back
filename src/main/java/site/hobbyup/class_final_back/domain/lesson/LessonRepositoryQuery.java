package site.hobbyup.class_final_back.domain.lesson;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonAllListRespDto;

@RequiredArgsConstructor
@Repository
public class LessonRepositoryQuery {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private EntityManager em;

  public List<LessonAllListRespDto> findAllLessonList(Long userId, Long categoryId, String sort, Long minPrice,
      Long maxPrice) {
    log.debug("디버그 : LessonRepositoryQuery - findAllLessonList실행");
    String sql = "select l.id as lessonId, l.name as lessonName,";
    sql += " l.price as lessonPrice,";
    sql += " COUNT(r.id) AS totalReviews,";
    sql += " (case when AVG(r.grade) IS null then 0.0 ELSE AVG(r.grade) END) AS avgGrade,";
    sql += " (case when s.lesson_id IS NOT NULL then true ELSE false END) AS subscribed,";
    sql += " (case when i.category_id IS NOT NULL then true ELSE false END) AS recommand,";
    sql += " (case when s2.count IS null then 0 ELSE s2.count END) AS ranking,";
    sql += " l.created_at AS recent";
    sql += " FROM lesson l LEFT OUTER JOIN review r ON l.id = r.lesson_id";
    sql += " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id = :userId) s";
    sql += " ON l.id = s.lesson_id";
    sql += " LEFT OUTER JOIN (SELECT category_id FROM interest WHERE user_id = :userId) i";
    sql += " ON l.category_id = i.category_id";
    sql += " LEFT OUTER JOIN (SELECT COUNT(*) count, lesson_id FROM subscribe GROUP BY lesson_id) s2";
    sql += " ON l.id = s2.lesson_id";
    sql += " WHERE l.category_id = :categoryId";
    sql += " GROUP BY lessonId";

    if (minPrice != 0L && maxPrice != 0L) {
      sql += " HAVING :minPrice < lessonPrice AND lessonPrice < :maxPrice";
    }

    if (sort.equals("recommand")) {
      sql += " ORDER BY recommand DESC";
    } else if (sort.equals("ranking")) {
      sql += " ORDER BY ranking DESC";
    } else if (sort.equals("recent")) {
      sql += " ORDER BY recent DESC";
    }
    log.debug("디버그 : sql = " + sql);

    // 쿼리 완성
    JpaResultMapper jpaResultMapper = new JpaResultMapper();
    Query query = em.createNativeQuery(sql)
        .setParameter("userId", userId)
        .setParameter("categoryId", categoryId);
    if (minPrice != 0L && maxPrice != 0L) {
      query.setParameter("minPrice", minPrice)
          .setParameter("maxPrice", maxPrice);
    }

    log.debug("디버그 : query = " + query);

    List<LessonAllListRespDto> result = jpaResultMapper.list(query, LessonAllListRespDto.class);
    log.debug("디버그 : result = " + result);
    return result;
  }
}
