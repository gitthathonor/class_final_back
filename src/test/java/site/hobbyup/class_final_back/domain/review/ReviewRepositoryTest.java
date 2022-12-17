package site.hobbyup.class_final_back.domain.review;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import site.hobbyup.class_final_back.config.dummy.DummyEntity;
import site.hobbyup.class_final_back.domain.user.UserRepository;

@ActiveProfiles("test")
@DataJpaTest // DB관련된 애들만 메모리에 뜸.
public class ReviewRepositoryTest extends DummyEntity {
  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EntityManager em;

  // // 전
  // @BeforeEach
  // public void setUp() {
  // // 유저 만들기
  // User ssar = userRepository.save(newUser("ssar"));

  // }

  // // 후
  // @AfterEach // JpaTest는 Transactional 어노테이션이 있어서 메서드 실행 직후 자동 롤백됨.
  // public void tearDown() {
  // // auto_increment 1로 초기화
  // em.createNativeQuery("ALTER TABLE users ALTER COLUMN `id` RESTART WITH
  // 1").executeUpdate();
  // // 메서드 종료될 때마다 롤백되고 autoincrement 초기화
  // }
}
