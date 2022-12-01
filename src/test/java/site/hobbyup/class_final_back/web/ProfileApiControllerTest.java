package site.hobbyup.class_final_back.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.tomcat.util.codec.binary.Base64;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.hobbyup.class_final_back.config.dummy.DummyEntity;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileSaveReqDto;
import site.hobbyup.class_final_back.dto.profile.ProfileReqDto.ProfileUpdateReqDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ProfileApiControllerTest extends DummyEntity {

        private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";
        private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=utf-8";

        @Autowired
        private MockMvc mvc;

        @Autowired
        private ObjectMapper om;

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ProfileRepository profileRepository;

        @BeforeEach
        public void setUp() {
                User ssar = userRepository.save(newUser("ssar"));
                User cos = userRepository.save(newUser("cos"));
                Profile cosProfile = profileRepository.save(newProfile(cos));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void saveProfile_test() throws Exception {
                // given
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

        @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void detailProfile_test() throws Exception {
                // given
                Long userId = 2L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/profile"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.user.id").value(2L));
                resultActions.andExpect(jsonPath("$.data.careerYear").value("신입"));
        }

        // @WithUserDetails(value = "cos", setupBefore =
        // TestExecutionEvent.TEST_EXECUTION)
        // @Test
        // public void updateProfile_test() throws Exception {
        // // given
        // Long userId = 2L;
        // ProfileUpdateReqDto profileUpdateReqDto = new ProfileUpdateReqDto();
        // String encodeFile = "aGVsbG8=";
        // byte[] decodeByte = Base64.decodeBase64(encodeFile);
        // String filePath = "C:\\Temp\\upload\\" + decodeByte + ".jpg";

        // profileUpdateReqDto.setCertification("컴활");
        // profileUpdateReqDto.setRegion("서울");
        // profileUpdateReqDto.setFilePath(filePath);

        // String requestBody = om.writeValueAsString(profileUpdateReqDto);

        // // when
        // ResultActions resultActions = mvc
        // .perform(put("/api/user/" + userId + "/profile").content(requestBody)
        // .contentType(APPLICATION_JSON_UTF8));
        // String responseBody =
        // resultActions.andReturn().getResponse().getContentAsString();
        // System.out.println("디버그 : " + responseBody);

        // // then
        // resultActions.andExpect(status().isCreated());
        // resultActions.andExpect(jsonPath("$.data.id").value(2L));
        // }
}
