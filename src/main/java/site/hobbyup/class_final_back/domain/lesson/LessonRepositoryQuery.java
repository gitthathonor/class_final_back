package site.hobbyup.class_final_back.domain.lesson;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonBuyingByUserRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonCategoryListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSearchListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSubscribedListRespDto;

@RequiredArgsConstructor
@Repository
public class LessonRepositoryQuery {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private EntityManager em;

  // 로그인 시, 카테고리 별 레슨 리스트
  public List<LessonCategoryListRespDto> findAllLessonCategoryListByUserId(Long userId, Long categoryId, String sort,
      Long minPrice,
      Long maxPrice) {
    log.debug("디버그 : LessonRepositoryQuery - findAllLessonCategoryListByUserId실행");
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

    if (minPrice != 0L || maxPrice != 0L) {
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
    if (minPrice != 0L || maxPrice != 0L) {
      query.setParameter("minPrice", minPrice)
          .setParameter("maxPrice", maxPrice);
    }

    log.debug("디버그 : query = " + query);

    List<LessonCategoryListRespDto> result = jpaResultMapper.list(query, LessonCategoryListRespDto.class);
    log.debug("디버그 : result = " + result);
    return result;
  }

  // 비로그인 시, 카테고리 별 레슨 리스트
  public List<LessonCategoryListRespDto> findAllLessonCategoryList(Long categoryId, String sort,
      Long minPrice,
      Long maxPrice) {
    log.debug("디버그 : LessonRepositoryQuery - findAllLessonCategoryList실행");
    String sql = "select l.id as lessonId, l.name as lessonName,";
    sql += " l.price as lessonPrice,";
    sql += " COUNT(r.id) AS totalReviews,";
    sql += " (case when AVG(r.grade) IS null then 0.0 ELSE AVG(r.grade) END) AS avgGrade,";
    sql += " (case when s.lesson_id IS NOT NULL then true ELSE false END) AS subscribed,";
    sql += " (case when i.category_id IS NOT NULL then true ELSE false END) AS recommand,";
    sql += " (case when s2.count IS null then 0 ELSE s2.count END) AS ranking,";
    sql += " l.created_at AS recent";
    sql += " FROM lesson l LEFT OUTER JOIN review r ON l.id = r.lesson_id";
    sql += " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id IS null) s";
    sql += " ON l.id = s.lesson_id";
    sql += " LEFT OUTER JOIN (SELECT category_id FROM interest) i";
    sql += " ON l.category_id = i.category_id";
    sql += " LEFT OUTER JOIN (SELECT COUNT(*) count, lesson_id FROM subscribe GROUP BY lesson_id) s2";
    sql += " ON l.id = s2.lesson_id";
    sql += " WHERE l.category_id = :categoryId";
    sql += " GROUP BY lessonId";

    if (minPrice != 0L || maxPrice != 0L) {
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
        .setParameter("categoryId", categoryId);
    if (minPrice != 0L || maxPrice != 0L) {
      query.setParameter("minPrice", minPrice)
          .setParameter("maxPrice", maxPrice);
    }

    log.debug("디버그 : query = " + query);

    List<LessonCategoryListRespDto> result = jpaResultMapper.list(query, LessonCategoryListRespDto.class);
    log.debug("디버그 : result = " + result);
    return result;
  }

  // 로그인 시, 검색했을 때
  public List<LessonSearchListRespDto> findAllLessonByKeyword(Long userId, @Param("keyword") String keyword) {
    log.debug("디버그 : LessonRepositoryQuery - findAllLessonByKeyword실행");

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
    sql += " LEFT OUTER JOIN (SELECT category_id FROM interest) i";
    sql += " ON l.category_id = i.category_id";
    sql += " LEFT OUTER JOIN (SELECT COUNT(*) count, lesson_id FROM subscribe GROUP BY lesson_id) s2";
    sql += " ON l.id = s2.lesson_id";
    sql += " WHERE l.name LIKE :keyword";
    sql += " GROUP BY lessonId";
    sql += " ORDER BY recent DESC";

    log.debug("디버그 : sql = " + sql);

    // 쿼리 완성
    JpaResultMapper jpaResultMapper = new JpaResultMapper();
    Query query = em.createNativeQuery(sql)
        .setParameter("userId", userId)
        .setParameter("keyword", "%" + keyword + "%");

    log.debug("디버그 : query = " + query);

    List<LessonSearchListRespDto> result = jpaResultMapper.list(query, LessonSearchListRespDto.class);
    log.debug("디버그 : result = " + result);
    return result;
  }

  // 비로그인 시, 검색했을 때
  public List<LessonSearchListRespDto> findAllLessonWithNotLoginByKeyword(@Param("keyword") String keyword) {
    log.debug("디버그 : LessonRepositoryQuery - findAllLessonByKeyword실행");

    String sql = "select l.id as lessonId, l.name as lessonName,";
    sql += " l.price as lessonPrice,";
    sql += " COUNT(r.id) AS totalReviews,";
    sql += " (case when AVG(r.grade) IS null then 0.0 ELSE AVG(r.grade) END) AS avgGrade,";
    sql += " (case when s.lesson_id IS NOT NULL then true ELSE false END) AS subscribed,";
    sql += " (case when i.category_id IS NOT NULL then true ELSE false END) AS recommand,";
    sql += " (case when s2.count IS null then 0 ELSE s2.count END) AS ranking,";
    sql += " l.created_at AS recent";
    sql += " FROM lesson l LEFT OUTER JOIN review r ON l.id = r.lesson_id";
    sql += " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id IS null) s";
    sql += " ON l.id = s.lesson_id";
    sql += " LEFT OUTER JOIN (SELECT category_id FROM interest) i";
    sql += " ON l.category_id = i.category_id";
    sql += " LEFT OUTER JOIN (SELECT COUNT(*) count, lesson_id FROM subscribe GROUP BY lesson_id) s2";
    sql += " ON l.id = s2.lesson_id";
    sql += " WHERE l.name LIKE :keyword";
    sql += " GROUP BY lessonId";
    sql += " ORDER BY recent DESC";

    log.debug("디버그 : sql = " + sql);

    // 쿼리 완성
    JpaResultMapper jpaResultMapper = new JpaResultMapper();
    Query query = em.createNativeQuery(sql)
        .setParameter("keyword", "%" + keyword + "%");

    log.debug("디버그 : query = " + query);

    List<LessonSearchListRespDto> result = jpaResultMapper.list(query, LessonSearchListRespDto.class);
    log.debug("디버그 : result = " + result);
    return result;
  }

  // 찜한 클래스 목록
  public List<LessonSubscribedListRespDto> findAllLessonBySubscribed(Long userId) {
    log.debug("디버그 : LessonRepositoryQuery - findAllLessonBySubscribed실행");

    String sql = "SELECT l.id AS lessonId,";
    sql += " l.name AS lessonName,";
    sql += " l.price AS lessonPrice,";
    sql += " r.totalReview AS totalReviews,";
    sql += " r.avgGrade AS avgGrade,";
    sql += " (case when s.id IS NOT NULL then true ELSE false END) AS subscribed";
    sql += " FROM lesson l INNER JOIN subscribe s ON l.id = s.lesson_id";
    sql += " INNER JOIN (SELECT COUNT(id) AS totalReview, AVG(grade) AS avgGrade, lesson_id as lessonId FROM review GROUP BY lesson_id) r";
    sql += " ON l.id = r.lessonId";
    sql += " WHERE s.user_id = :userId";
    sql += " GROUP BY l.id";
    sql += " ORDER BY s.created_at DESC";

    log.debug("디버그 : sql = " + sql);

    // 쿼리 완성
    JpaResultMapper jpaResultMapper = new JpaResultMapper();
    Query query = em.createNativeQuery(sql)
        .setParameter("userId", userId);

    log.debug("디버그 : query = " + query);

    List<LessonSubscribedListRespDto> result = jpaResultMapper.list(query, LessonSubscribedListRespDto.class);
    log.debug("디버그 : result = " + result);
    return result;
  }

  // 일반회원 수강중인 레슨 목록 보기
  public List<LessonBuyingByUserRespDto> findAllLessonWithPayment(Long userId) {
    log.debug("디버그 : LessonRepositoryQuery - findAllLessonBySubscribed실행");

    String sql = "SELECT l.id AS lessonId, l.photo AS lessonPhoto, l.name AS lessonName, l.price AS lessonPrice, l.deadline AS lessonDeadline";
    sql += " FROM lesson l INNER JOIN (SELECT distinct lesson_id from payment where user_id = :userId) p";
    sql += " ON l.id = p.lesson_id;";

    log.debug("디버그 : sql = " + sql);

    // 쿼리 완성
    JpaResultMapper jpaResultMapper = new JpaResultMapper();
    Query query = em.createNativeQuery(sql)
        .setParameter("userId", userId);

    log.debug("디버그 : query = " + query);

    List<LessonBuyingByUserRespDto> result = jpaResultMapper.list(query, LessonBuyingByUserRespDto.class);
    log.debug("디버그 : result = " + result);
    return result;
  }

}
