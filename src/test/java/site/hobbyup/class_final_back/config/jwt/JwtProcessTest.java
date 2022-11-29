package site.hobbyup.class_final_back.config.jwt;

import org.junit.jupiter.api.Test;

import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.user.User;

public class JwtProcessTest {

  @Test
  public void create_test() throws Exception {
    // given
    User user = User.builder().id(1L).role(UserEnum.USER).build();
    LoginUser loginUser = new LoginUser(user);
    String token = JwtProcess.create(loginUser);
    System.out.println(token);
  }
}
