package site.hobbyup.class_final_back.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.jwt.JwtAuthenticationFilter;
import site.hobbyup.class_final_back.config.jwt.JwtAuthorizationFilter;
import site.hobbyup.class_final_back.util.CustomResponseUtil;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : passwordEncoder Bean 등록됨");
        return new BCryptPasswordEncoder();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            log.debug("디버그 : SecurityConfig의 configure");
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(new JwtAuthenticationFilter(authenticationManager));
            http.addFilter(new JwtAuthorizationFilter(authenticationManager));
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : SecurityConfig의 filterChain");
        http.headers().frameOptions().disable();
        http.csrf().disable();

        // 인증권한 확인 필터
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) ->

        {
            CustomResponseUtil.fail(response, "권한없음");
        });

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();
        http.apply(new MyCustomDsl());
        http.authorizeHttpRequests()
                .antMatchers("/api/user/**").authenticated()
                .anyRequest().permitAll();

        return http.build();
    }
}
