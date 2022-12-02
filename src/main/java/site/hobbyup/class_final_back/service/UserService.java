package site.hobbyup.class_final_back.service;

import java.util.List;

import org.h2.value.lob.LobData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.interest.Interest;
import site.hobbyup.class_final_back.domain.interest.InterestRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.user.UserReqDto.JoinReqDto;
import site.hobbyup.class_final_back.dto.user.UserReqDto.UserUpdateReqDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.JoinRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.UserUpdateRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final CategoryRepository categoryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public JoinRespDto join(JoinReqDto joinReqDto) {
        log.debug("디버그 : UserService-join 실행됨");

        // 1. 비밀번호 암호화
        String rawPassword = joinReqDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        joinReqDto.setPassword(encPassword);
        // 2. 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity());

        // 3. 관심사 저장
        log.debug("디버그 : JoinReqDto가 선택한 카테고리들 : " + joinReqDto.getCategoryIds());
        List<Category> categoryListPS = categoryRepository.findAllById(joinReqDto.getCategoryIds());

        for (Category category : categoryListPS) {
            log.debug("디버그 : 유저가 선택한 카테고리" + category.getId());
            Interest interest = Interest.builder().category(category).user(userPS).build();
            interestRepository.save(interest);
        }

        // 4. 카테고리를 다시 select함
        List<Interest> interestListPS = interestRepository.findAllByUserId(userPS.getId());

        // 4. DTO 응답
        return new JoinRespDto(userPS, interestListPS);
    }

    // 회원정보수정
    @Transactional
    public UserUpdateRespDto updateUser(UserUpdateReqDto userUpdateReqDto, Long id) {
        log.debug("디버그 : UserService-updateUser 실행됨");

        // 회원이 DB에 존재하는지 확인
        User userOP = userRepository.findById(id)
                .orElseThrow(() -> new CustomApiException("가입되지 않은 유저입니다.", HttpStatus.FORBIDDEN));
        log.debug("디버그 : userOP의 id : " + userOP.getId());
        String rawPassword = userUpdateReqDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        userUpdateReqDto.setPassword(encPassword);

        userOP.update(userUpdateReqDto);

        return new UserUpdateRespDto(userRepository.save(userOP));
    }

    // 회원탈퇴
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteUser(Long id) {
        User userPS = userRepository.findById(id)
                .orElseThrow(() -> new CustomApiException("가입되지 않은 유저입니다.", HttpStatus.FORBIDDEN));

        userRepository.deleteById(userPS.getId());
    }

}
