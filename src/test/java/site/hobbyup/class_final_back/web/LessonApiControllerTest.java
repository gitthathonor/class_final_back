package site.hobbyup.class_final_back.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;

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
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.interest.Interest;
import site.hobbyup.class_final_back.domain.interest.InterestRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.review.ReviewRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonUpdateReqDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class LessonApiControllerTest extends DummyEntity {

  private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper om;

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private ProfileRepository profileRepository;

  @Autowired
  private SubscribeRepository subscribeRepository;

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

    Profile ssarProfile = profileRepository
        .save(newProfile("", "안녕하세요 부산에서 가장 뷰티한 강사 ssar입니다.", "부산", "미용사", "5년", "박준 뷰티랩 양정점 원장 10년", ssar));

    Profile hongProfile = profileRepository
        .save(newProfile("", "hong의 프로필입니다.", "울산", "네일아트 자격증", "10년", "각종 다수의 숍에서 일한 경력", hong));

    Lesson lesson1 = lessonRepository.save(newLesson("더미1", 10000L, expert1, beauty));
    Lesson lesson2 = lessonRepository.save(newLesson("더미2", 20000L, expert1, sports));
    Lesson lesson3 = lessonRepository.save(newLesson("더미3", 50000L, expert1, music));
    Lesson lesson4 = lessonRepository.save(newLesson("더미4", 34500L, expert1, music));
    Lesson lesson5 = lessonRepository.save(newLesson("더미5", 2400L, expert1, music));
    Lesson lesson6 = lessonRepository.save(newLesson("더미6", 98000000L, expert1, beauty));
    Lesson lesson7 = lessonRepository.save(newLesson("더미7", 30000L, expert1, sports));
    Lesson lesson8 = lessonRepository.save(newLesson("더미8", 40000L, expert1, sports));
    Lesson lesson9 = lessonRepository.save(newLesson("더미9", 50000L, expert1, sports));
    Lesson lesson10 = lessonRepository.save(newLesson("더미10", 70000L, expert1, sports));
    // Lesson lesson11 = lessonRepository.save(newLesson("더미11", 80000L, expert1,
    // beauty));
    // Lesson lesson12 = lessonRepository.save(newLesson("더미12", 90000L, expert1,
    // dance));
    // Lesson lesson13 = lessonRepository.save(newLesson("더미13", 2000L, expert1,
    // beauty));
    // Lesson lesson14 = lessonRepository.save(newLesson("더미14", 94000L, expert1,
    // beauty));
    // Lesson lesson15 = lessonRepository.save(newLesson("더미15", 7574000L, expert1,
    // music));

    Review review1 = reviewRepository.save(newReivew("너무 좋은 강의입니다.", 4.5, ssar, lesson1));
    Review review2 = reviewRepository.save(newReivew("생각했던 것보다 더 좋네요!", 4.0, cos, lesson1));
    Review review3 = reviewRepository.save(newReivew("별로네요", 3.0, ssar, lesson2));
    Review review4 = reviewRepository.save(newReivew("도대체 이 강의 하시는 이유가 뭐죠?", 2.5, ssar, lesson3));
    Review review5 = reviewRepository.save(newReivew("피곤하다", 2.0, cos, lesson2));
    Review review6 = reviewRepository.save(newReivew("이정도 퀄리티면 좋은 거 같아요, 다만 목소리가...", 3.5, cos, lesson8));
    Review review7 = reviewRepository.save(newReivew("토큰 없음", 1.5, cos, lesson7));

    Subscribe subscribe1 = subscribeRepository.save(newSubscribe(ssar, lesson1));
    Subscribe subscribe2 = subscribeRepository.save(newSubscribe(ssar, lesson2));
    Subscribe subscribe3 = subscribeRepository.save(newSubscribe(cos, lesson3));
    Subscribe subscribe4 = subscribeRepository.save(newSubscribe(cos, lesson8));
    Subscribe subscribe5 = subscribeRepository.save(newSubscribe(cos, lesson9));

  }

  @WithUserDetails(value = "expert", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void saveLesson_test() throws Exception {
    // given
    LessonSaveReqDto lessonSaveReqDto = new LessonSaveReqDto();
    String realPhoto = "";
    String photo = DecodeUtil.saveDecodingImage(realPhoto);

    lessonSaveReqDto.setName("프로작곡가가 알려주는 하루만에 미디 작곡하는 법");
    lessonSaveReqDto.setCategoryId(4L);
    lessonSaveReqDto.setCurriculum("1차 : 작곡가 소개, 2차 : 미디 사용법 숙지, 3차 : 실제 곡 만들기");
    lessonSaveReqDto.setPhoto(photo);
    lessonSaveReqDto.setPlace("부산진구");
    lessonSaveReqDto.setDeadline(new Timestamp(700000000L));
    lessonSaveReqDto.setPolicy("취소 및 환불정책");
    lessonSaveReqDto.setPossibleDays("월요일,수요일,금요일");
    lessonSaveReqDto.setPrice(500000L);

    String requestBody = om.writeValueAsString(lessonSaveReqDto);

    // when
    ResultActions resultActions = mvc
        .perform(post("/api/lesson").content(requestBody)
            .contentType(APPLICATION_JSON_UTF8));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.data.name").value("프로작곡가가 알려주는 하루만에 미디 작곡하는 법"));
    resultActions.andExpect(jsonPath("$.data.categoryName").value("음악"));
    resultActions.andExpect(jsonPath("$.data.expertId").value(1L));
    resultActions.andExpect(jsonPath("$.data.id").value(11L));
  }

  @Test
  public void getLessonCategoryList_test() throws Exception {
    // given
    Long categoryId = 2L;
    Long minPrice = 5000L;
    Long maxPrice = 50000L;

    // when
    ResultActions resultActions = mvc
        .perform(get("/api/category/test/" + categoryId + "?min_price=" + minPrice + "&max_price=" + maxPrice));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.data.categoryDto.categoryName").value("스포츠"));
    resultActions.andExpect(jsonPath("$.data.lessonDtoList[0].lessonPrice").value("20000원"));
  }

  @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void getLessonDetail_test() throws Exception {
    // given
    Long lessonId = 1L;

    // when
    ResultActions resultActions = mvc
        .perform(get("/api/category/lesson/" + lessonId));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.data.lessonDto.lessonName").value("더미1"));
    resultActions.andExpect(jsonPath("$.data.lessonReviewList[0].reviewContent").value("생각했던 것보다 더 좋네요!"));
    resultActions.andExpect(jsonPath("$.data.subscribed").value(false));
  }

  // @WithUserDetails(value = "ssar", setupBefore =
  // TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void getLessonCommonList_test() throws Exception {
    // when
    ResultActions resultActions = mvc
        .perform(get("/api/main"));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.data[0].lessonPrice").value(70000L));
    resultActions.andExpect(jsonPath("$.data[9].avgGrade").value(4.25));
    resultActions.andExpect(jsonPath("$.data[8].subscribed").value(false));
    resultActions.andExpect(jsonPath("$.data[8].totalReview").value(2L));

  }

  @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void getLessonSubscribeList_test() throws Exception {
    // when
    ResultActions resultActions = mvc
        .perform(get("/api/lesson/subscribe"));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.data[0].lessonPrice").value(10000L));
    resultActions.andExpect(jsonPath("$.data[0].avgGrade").value(4.25));
    resultActions.andExpect(jsonPath("$.data[1].subscribed").value(true));
    resultActions.andExpect(jsonPath("$.data[2].totalReview").value(1L));

  }

  @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void updateLesson_test() throws Exception {
    // given

    Long id = 1L;
    LessonUpdateReqDto lessonUpdateReqDto = new LessonUpdateReqDto();
    String realPhoto = "";
    String photo = DecodeUtil.saveDecodingImage(realPhoto);

    lessonUpdateReqDto.setName("200만 뷰티 유튜버가 알려드리는 화장법");
    lessonUpdateReqDto.setCategoryId(1L);
    lessonUpdateReqDto.setCurriculum("주 2회, 1시간 동안 고객님에게 딱 맞는 화장법을 캐치해드립니다.");
    lessonUpdateReqDto.setPhoto(photo);
    lessonUpdateReqDto.setPlace("강남구");
    lessonUpdateReqDto.setDeadline(new Timestamp(8225L));
    lessonUpdateReqDto.setPolicy("취소 및 환불정책");
    lessonUpdateReqDto.setPossibleDays("월요일,화요일,수요일,목요일");
    lessonUpdateReqDto.setPrice(250000L);

    String requestBody = om.writeValueAsString(lessonUpdateReqDto);

    // when
    ResultActions resultActions = mvc
        .perform(put("/api/lesson/" + id).content(requestBody)
            .contentType(APPLICATION_JSON_UTF8));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.data.name").value("200만 뷰티 유튜버가 알려드리는 화장법"));
    resultActions.andExpect(jsonPath("$.data.categoryName").value("뷰티"));
    resultActions.andExpect(jsonPath("$.data.expertId").value(1L));
    resultActions.andExpect(jsonPath("$.data.id").value(1L));

  }

  @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void getLessonListByCategoryWithSort_test() throws Exception {
    // given
    Long categoryId = 1L;

    // when
    ResultActions resultActions = mvc
        .perform(get("/api/category/" + categoryId));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    // resultActions.andExpect(jsonPath("$.data.lessonName").value("더미1"));
    // resultActions.andExpect(jsonPath("$.data.").value("생각했던 것보다 더 좋네요!"));

  }

}
