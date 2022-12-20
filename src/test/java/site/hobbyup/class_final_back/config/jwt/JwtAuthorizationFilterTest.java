package site.hobbyup.class_final_back.config.jwt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.user.User;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class JwtAuthorizationFilterTest {
  @Autowired
  private MockMvc mvc;

  @Test
  public void authorization_success_test() throws Exception {
    // given
    User user = User.builder().id(1L).role(UserEnum.USER).build();
    LoginUser loginUser = new LoginUser(user);
    String jwtToken = JwtProcess.create(loginUser);
    System.out.println("테스트 : " + jwtToken);

    // when
    ResultActions resultActions = mvc
        .perform(get("/api/user/test/123").header(JwtProperties.HEADER_KEY, jwtToken));

    // then
    resultActions.andExpect(status().isNotFound());
  }

  @Test
  public void authorization_fail_test() throws Exception {
    // given

    // when
    ResultActions resultActions = mvc
        .perform(get("/api/user/test"));

    // then
    resultActions.andExpect(status().isForbidden());
  }
}
