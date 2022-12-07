package site.hobbyup.class_final_back.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.coupon.Coupon;
import site.hobbyup.class_final_back.domain.coupon.CouponRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.payment.PaymentRepository;
import site.hobbyup.class_final_back.domain.paymentType.PaymentType;
import site.hobbyup.class_final_back.domain.paymentType.PaymentTypeRepository;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.review.ReviewRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.payment.PaymentReqDto.PaymentSaveReqDto;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class PaymentApiControllerTest extends DummyEntity {

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
  private PaymentRepository paymentRepository;

  @Autowired
  private PaymentTypeRepository paymentTypeRepository;

  @Autowired
  private CouponRepository couponRepository;

  @Autowired
  private SubscribeRepository subscribeRepository;

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

    Profile ssarProfile = profileRepository
        .save(newProfile("", "안녕하세요 부산에서 가장 뷰티한 강사 ssar입니다.", "부산", "미용사", "5년", "박준 뷰티랩 양정점 원장 10년", ssar));

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

    PaymentType card = paymentTypeRepository.save(newPaymentType("신용카드"));
    PaymentType kakaoPay = paymentTypeRepository.save(newPaymentType("카카오페이"));
    PaymentType vbank = paymentTypeRepository.save(newPaymentType("무통장입금"));

    Subscribe subscribe1 = subscribeRepository.save(newSubscribe(ssar, lesson1));

    Coupon coupon1 = couponRepository.save(newCoupon("회원가입 쿠폰", 1000L, "2022-12-22", ssar));

  }

  @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void savePayment_test() throws Exception {
    // given
    Long lessonId = 1L;
    PaymentSaveReqDto paymentSaveReqDto = new PaymentSaveReqDto();
    paymentSaveReqDto.setTotalCount(2);
    paymentSaveReqDto.setPaymentTypeId(1L);
    String requestBody = om.writeValueAsString(paymentSaveReqDto);

    // when
    ResultActions resultActions = mvc
        .perform(post("/api/lesson/" + lessonId + "/payment").content(requestBody)
            .contentType(APPLICATION_JSON_UTF8));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.data.finalPrice").value(18000L));
    resultActions.andExpect(jsonPath("$.data.paymentTypeName").value("신용카드"));
  }
}
