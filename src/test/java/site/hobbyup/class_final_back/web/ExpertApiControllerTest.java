package site.hobbyup.class_final_back.web;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.hobbyup.class_final_back.config.dummy.DummyEntity;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.interest.Interest;
import site.hobbyup.class_final_back.domain.interest.InterestRepository;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.lesson.LessonRepositoryQuery;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.review.ReviewRepository;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ExpertApiControllerTest extends DummyEntity {
  private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper om;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ExpertRepository expertRepository;

  @Autowired
  private InterestRepository interestRepository;

  @Autowired
  private EntityManager em;

  @BeforeEach
  public void setUp() {
    Category beauty = categoryRepository.save(newCategory("뷰티"));
    Category sports = categoryRepository.save(newCategory("스포츠"));
    Category dance = categoryRepository.save(newCategory("댄스"));
    Category music = categoryRepository.save(newCategory("음악"));
    Category art = categoryRepository.save(newCategory("미술"));
    Category crafts = categoryRepository.save(newCategory("공예"));
    Category game = categoryRepository.save(newCategory("게임"));
    Category others = categoryRepository.save(newCategory("기타"));

    User ssar = userRepository.save(newUser("ssar"));
    User cos = userRepository.save(newUser("cos"));
    User hong = userRepository.save(newUser("expert"));

    Interest ssarInterest = interestRepository.save(newInterest(ssar, beauty));
    Interest ssarInterest2 = interestRepository.save(newInterest(ssar, sports));
    Interest ssarInterest3 = interestRepository.save(newInterest(ssar, dance));

    Expert expert1 = expertRepository.save(newExpert(hong));

  }

  @Test
  public void _test() throws Exception {
    // given
    Long userId = 1L;

    // when

    // then

  }

}
