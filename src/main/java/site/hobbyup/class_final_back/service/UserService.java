package site.hobbyup.class_final_back.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.user.UserReqDto.JoinReqDto;
import site.hobbyup.class_final_back.dto.user.UserReqDto.UpdateReqDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.JoinRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.UpdateRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public JoinRespDto join(JoinReqDto joinReqDto) {
        log.debug("디버그 : UserService-join 실행됨");

        // 1. 비밀번호 암호화
        String rawPassword = joinReqDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        joinReqDto.setPassword(encPassword);
        // 2. 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity());
        // 3. DTO 응답
        return new JoinRespDto(userPS);
    }

    @Transactional
    public UpdateRespDto updateUser(UpdateReqDto updateReqDto, Long id) {
        log.debug("디버그 : UserService-updateUser 실행됨");

        // 먼저 id에 해당하는 유저 정보가 있는지 확인
        // Optional<User> userOP = userRepository.findById(id);
        // if (userOP.get() == null) {
        // return new CustomApiException("", null);
        // }

        // 1. 비밀번호 암호화
        String rawPassword = updateReqDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        updateReqDto.setPassword(encPassword);
        // 2. 회원가입
        User userPS = userRepository.save(updateReqDto.toEntity());
        return new UpdateRespDto(userPS);
    }
}
