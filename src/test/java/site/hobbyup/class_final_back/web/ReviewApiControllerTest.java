package site.hobbyup.class_final_back.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.payment.Payment;
import site.hobbyup.class_final_back.domain.payment.PaymentRepository;
import site.hobbyup.class_final_back.domain.paymentType.PaymentType;
import site.hobbyup.class_final_back.domain.paymentType.PaymentTypeRepository;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.review.ReviewRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.review.ReviewReqDto.ReviewSaveReqDto;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ReviewApiControllerTest extends DummyEntity {

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
  private ExpertRepository expertRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private PaymentTypeRepository paymentTypeRepository;

  @Autowired
  private CouponRepository couponRepository;

  @Autowired
  private EntityManager em;

  @BeforeEach
  public void setUp() {
    User ssar = userRepository.save(newUser("ssar"));
    User cos = userRepository.save(newUser("cos"));
    User hong = userRepository.save(newUser("expert"));

    Expert expert1 = expertRepository.save(newExpert(hong));

    Category beauty = categoryRepository.save(newCategory("뷰티"));
    Category sports = categoryRepository.save(newCategory("스포츠"));
    Category dance = categoryRepository.save(newCategory("댄스"));
    Category music = categoryRepository.save(newCategory("음악"));
    Category art = categoryRepository.save(newCategory("미술"));
    Category crafts = categoryRepository.save(newCategory("공예"));
    Category game = categoryRepository.save(newCategory("게임"));
    Category others = categoryRepository.save(newCategory("기타"));

    PaymentType card = paymentTypeRepository.save(newPaymentType("신용카드"));
    PaymentType vBank = paymentTypeRepository.save(newPaymentType("무통장입금"));
    PaymentType kakaoPay = paymentTypeRepository.save(newPaymentType("카카오페이"));

    Coupon ssarCoupon = couponRepository.save(newCoupon("ssar의 쿠폰", 1000L, "2022-12-25", ssar));

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

    Review review1 = reviewRepository.save(newReivew("너무 좋은 강의입니다.", 4.5, ssar, lesson1));
    Review review2 = reviewRepository.save(newReivew("생각했던 것보다 더 좋네요!", 4.0, cos, lesson1));
    Review review3 = reviewRepository.save(newReivew("별로네요", 3.0, ssar, lesson2));
    Review review4 = reviewRepository.save(newReivew("도대체 이 강의 하시는 이유가 뭐죠?", 2.5, ssar, lesson3));
    Review review5 = reviewRepository.save(newReivew("피곤하다", 2.0, cos, lesson2));
    Review review6 = reviewRepository.save(newReivew("이정도 퀄리티면 좋은 거 같아요, 다만 목소리가...", 3.5, cos, lesson8));

    Payment ssarPayment = paymentRepository.save(newPayment(ssar, lesson1, card, ssarCoupon, 1));

  }

  @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
  @Test
  public void saveReview_test() throws Exception {
    // given
    Long userId = 1L;
    Long lessonId = 1L;

    ReviewSaveReqDto reviewSaveReqDto = new ReviewSaveReqDto();
    reviewSaveReqDto.setContent("조인을 어떻게 해야할지 감이 안 잡힌다~");
    reviewSaveReqDto.setGrade(4.5);
    String requestBody = om.writeValueAsString(reviewSaveReqDto);

    // when
    ResultActions resultActions = mvc
        .perform(post("/api/user/" + userId + "/buyingList/" + lessonId + "/review").content(requestBody)
            .contentType(APPLICATION_JSON_UTF8));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.data.content").value("조인을 어떻게 해야할지 감이 안 잡힌다~"));
    resultActions.andExpect(jsonPath("$.data.grade").value(4.5));

  }

}
