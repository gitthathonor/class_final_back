package site.hobbyup.class_final_back.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
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
  private EntityManager em;

  @BeforeEach
  public void setUp() {
    User ssar = userRepository.save(newUser("ssar"));
    User cos = userRepository.save(newUser("cos"));

    Category beauty = categoryRepository.save(newCategory("뷰티"));
    Category sports = categoryRepository.save(newCategory("스포츠"));
    Category dance = categoryRepository.save(newCategory("댄스"));
    Category music = categoryRepository.save(newCategory("음악"));
    Category art = categoryRepository.save(newCategory("미술"));
    Category crafts = categoryRepository.save(newCategory("공예"));
    Category game = categoryRepository.save(newCategory("게임"));
    Category others = categoryRepository.save(newCategory("기타"));

    Lesson lesson1 = lessonRepository.save(newLesson("더미1", 10000L, ssar, beauty));
    Lesson lesson2 = lessonRepository.save(newLesson("더미2", 20000L, ssar, sports));
    Lesson lesson3 = lessonRepository.save(newLesson("더미3", 50000L, ssar, music));
    Lesson lesson4 = lessonRepository.save(newLesson("더미4", 34500L, cos, music));
    Lesson lesson5 = lessonRepository.save(newLesson("더미5", 2400L, cos, music));
    Lesson lesson6 = lessonRepository.save(newLesson("더미6", 98000000L, cos, beauty));
    Lesson lesson7 = lessonRepository.save(newLesson("더미7", 30000L, ssar, sports));
    Lesson lesson8 = lessonRepository.save(newLesson("더미8", 40000L, ssar, sports));
    Lesson lesson9 = lessonRepository.save(newLesson("더미9", 50000L, ssar, sports));
    Lesson lesson10 = lessonRepository.save(newLesson("더미10", 70000L, ssar, sports));
  }

  @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
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
    lessonSaveReqDto.setExpiredAt(new Timestamp(700000000L));
    lessonSaveReqDto.setPolicy("취소 및 환불정책");
    lessonSaveReqDto.setPossibleDays("월,화,수");
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
    resultActions.andExpect(jsonPath("$.data.category.name").value("음악"));
    resultActions.andExpect(jsonPath("$.data.user.id").value(1L));
    resultActions.andExpect(jsonPath("$.data.id").value(7L));
  }

  @Test
  public void getLessonCategoryList_test() throws Exception {
    // given
    Long categoryId = 1L;

    // when
    ResultActions resultActions = mvc
        .perform(get("/api/category/" + categoryId));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.data.categoryDto.categoryName").value("뷰티"));
    resultActions.andExpect(jsonPath("$.data.lessonDtoList[0].lessonPrice").value("10000원"));
  }

  @Test
  public void getLessonBudgetList_test() throws Exception {
    // given
    Long categoryId = 2L;
    Long minPrice = 5000L;
    Long maxPrice = 50000L;

    // when
    ResultActions resultActions = mvc
        .perform(get("/api/category/" + categoryId + "/value/?min_price=" + minPrice + "&max_price=" + maxPrice));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.data.categoryDto.categoryName").value("스포츠"));
    resultActions.andExpect(jsonPath("$.data.lessonDtoList[0].lessonPrice").value("20000원"));
    // resultActions.andExpect(jsonPath("$.data.lessonDtoList.size").value(3));
  }

}
