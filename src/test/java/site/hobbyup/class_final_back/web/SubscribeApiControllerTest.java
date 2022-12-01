package site.hobbyup.class_final_back.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.sql.Timestamp;

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
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileSaveReqDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeReqDto;
import site.hobbyup.class_final_back.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class SubscribeApiControllerTest extends DummyEntity {

        private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";
        private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=utf-8";

        @Autowired
        private MockMvc mvc;

        @Autowired
        private ObjectMapper om;

        @Autowired
        private SubscribeRepository subscribeRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ProfileRepository profileRepository;
        @Autowired
        private CategoryRepository categoryRepository;
        @Autowired
        private LessonRepository lessonRepository;

        @BeforeEach
        public void setUp() throws IOException {
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
                Lesson lesson1 = lessonRepository.save(lessonSaveReqDto.toEntity(others, cos));

        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void saveProfile_test() throws Exception {
                // given
                SubscribeSaveReqDto subscribeSaveReqDto = new SubscribeSaveReqDto();

                ProfileSaveReqDto profileSaveReqDto = new ProfileSaveReqDto();
                String encodeFile = "aGVsbG8=";
                String filePath = DecodeUtil.saveDecodingImage(encodeFile);

                profileSaveReqDto.setFilePath(filePath);
                profileSaveReqDto.setIntroduction("안녕하세요");
                profileSaveReqDto.setRegion("부산");
                profileSaveReqDto.setCertification("컴활");
                profileSaveReqDto.setCareerYear("신입");
                profileSaveReqDto.setCareer("없음");

                String requestBody = om.writeValueAsString(profileSaveReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/profile").content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);
                // then
                resultActions.andExpect(status().isCreated());
                resultActions.andExpect(jsonPath("$.data.id").value(2L));
        }

}
