package site.hobbyup.class_final_back.config.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.user.User;

public class DummyEntity {
  protected User newUser(String username) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encPassword = passwordEncoder.encode("1234");
    User user = User.builder()
        .username(username)
        .password(encPassword)
        .email(username + "@nate.com")
        .role(username.equals("admin") ? UserEnum.ADMIN : UserEnum.USER)
        .build();
    return user;
  }

  protected Profile newProfile(User user) {
    Profile profile = Profile.builder()
        .introduction("안녕하세요")
        .region("부산")
        .certification("컴활")
        .careerYear("신입")
        .career("없음")
        .user(user)
        .build();
    return profile;
  }
}