package site.hobbyup.class_final_back.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.hobbyup.class_final_back.config.dummy.DummyEntity;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.interest.Interest;
import site.hobbyup.class_final_back.domain.interest.InterestRepository;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
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
  private ProfileRepository profileRepository;

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

    User ssar = userRepository.save(newUser("ssar", UserEnum.USER));
    User cos = userRepository.save(newUser("cos", UserEnum.USER));
    User hong = userRepository.save(newUser("hong", UserEnum.EXPERT));

    Interest ssarInterest = interestRepository.save(newInterest(ssar, beauty));
    Interest ssarInterest2 = interestRepository.save(newInterest(ssar, sports));
    Interest ssarInterest3 = interestRepository.save(newInterest(ssar, dance));

    Expert expert1 = expertRepository.save(newExpert(hong));

    // Profile hongProfile = profileRepository
    // .save(newProfile("", "hong의 프로필입니다.", "울산", "네일아트 자격증", "10년", "각종 다수의 숍에서 일한
    // 경력", hong));

  }

  @WithUserDetails(value = "hong", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void getExpertPage_test() throws Exception {
    // given
    Long userId = 3L;

    // when
    ResultActions resultActions = mvc
        .perform(get("/api/expert/" + userId + "/mypage"));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.data.satisfaction").value(0L));
    resultActions.andExpect(jsonPath("$.data.approval").value(true));

  }

}
