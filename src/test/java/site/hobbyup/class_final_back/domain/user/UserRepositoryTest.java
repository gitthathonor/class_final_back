package site.hobbyup.class_final_back.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import site.hobbyup.class_final_back.config.enums.UserEnum;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
public class UserRepositoryTest {

    // @BeforeEach
    // public void setUp() {
    // dataInsert();
    // }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void findByUsername_test() {
        // given
        String username = "ssar";

        // when
        Optional<User> userOP = userRepository.findByUsername(username);

        // then
        assertEquals("ssar@nate.com", userOP.get().getEmail());

    }

    // public void dataInsert() {
    // User user =
    // User.builder().username("ssar").password(passwordEncoder.encode("1234")).email("ssar@nate.com")
    // .role(UserEnum.USER)
    // .phoneNum("01011112222").build();
    // System.out.println(user.getUsername());
    // System.out.println("디버그 : " + userRepository.save(user));
    // }
}
