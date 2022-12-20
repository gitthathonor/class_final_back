package site.hobbyup.class_final_back.config.auth;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;

@Service
public class LoginUserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("디버그 : loadUserByUsername실행됨");
        Optional<User> userOP = userRepository.findByUsername(username);
        if (userOP.isPresent()) {
            return new LoginUser(userOP.get());
        } else {
            return null;
        }
    }

}
