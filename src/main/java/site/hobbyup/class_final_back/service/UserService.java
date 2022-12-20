package site.hobbyup.class_final_back.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.coupon.Coupon;
import site.hobbyup.class_final_back.domain.coupon.CouponRepository;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.interest.Interest;
import site.hobbyup.class_final_back.domain.interest.InterestRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.user.UserReqDto.JoinReqDto;
import site.hobbyup.class_final_back.dto.user.UserReqDto.UserUpdateReqDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.JoinRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.MyLessonListRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.MyPageRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.UserDeleteRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.UserInitRespDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.UserUpdateRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;
    private final CouponRepository couponRepository;
    private final LessonRepository lessonRepository;
    private final ExpertRepository expertRepository;
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

        if (joinReqDto.getCategoryIds() == null) {
            return new JoinRespDto(userPS);
        }
        List<Category> categoryListPS = categoryRepository.findAllById(joinReqDto.getCategoryIds());

        for (Category category : categoryListPS) {
            log.debug("디버그 : 유저가 선택한 카테고리" + category.getId());
            Interest interest = Interest.builder().category(category).user(userPS).build();
            interestRepository.save(interest);
        }

        // 4. 카테고리를 다시 select함
        List<Interest> interestListPS = interestRepository.findAllByUserId(userPS.getId());

        // 5. 쿠폰 증정
        Coupon coupon = Coupon.builder().title("회원가입 쿠폰").price(10000L).expiredDate("2022-12-22").user(userPS).build();
        couponRepository.save(coupon);

        // 6. role=expert일 시, expert 테이블에도 추가 입력
        if (userPS.getRole().getValue().equals("전문가")) {
            expertRepository
                    .save(Expert.builder()
                            .satisfaction(0L)
                            .totalLesson(0L)
                            .isApproval(false)
                            .user(userPS)
                            .build());
        }

        // 7. DTO 응답
        return new JoinRespDto(userPS, interestListPS);
    }

    // 회원정보수정
    @Transactional
    public UserUpdateRespDto updateUser(UserUpdateReqDto userUpdateReqDto, Long id) {
        log.debug("디버그 : UserService-updateUser 실행됨");

        // 회원 영속화
        User userPS = userRepository.findById(id)
                .orElseThrow(() -> new CustomApiException("가입되지 않은 유저입니다.", HttpStatus.FORBIDDEN));
        log.debug("디버그 : userPS의 id : " + userPS.getId());
        String rawPassword = userUpdateReqDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        userUpdateReqDto.setPassword(encPassword);

        // ReqDto에서 받은 카테고리 id로 참조
        List<Category> categoryListPS = categoryRepository.findAllById(userUpdateReqDto.getCategoryIds());

        // 관심사 테이블 영속화
        List<Interest> interestPS = interestRepository.findAllByUserId(userPS.getId());

        // 관심사 테이블의 카테고리들을 업데이트 시켜주기
        for (int i = 0; i < interestPS.size(); i++) {
            interestPS.get(i).update(categoryListPS.get(i));
        }
        interestRepository.saveAll(interestPS);

        userPS.update(userUpdateReqDto);

        return new UserUpdateRespDto(userRepository.save(userPS), interestPS);
    }

    // 회원탈퇴
    @Transactional(rollbackFor = RuntimeException.class)
    public UserDeleteRespDto deleteUser(Long id) {
        User userPS = userRepository.findById(id)
                .orElseThrow(() -> new CustomApiException("가입되지 않은 유저입니다.", HttpStatus.FORBIDDEN));
        // 쿠폰 삭제
        List<Coupon> couponList = couponRepository.findAllByUserId(id);
        couponRepository.deleteAll(couponList);

        // 회원 탈퇴
        userPS.delete();
        return new UserDeleteRespDto(userRepository.save(userPS));
    }

    // 마이페이지 메인
    @Transactional
    public MyPageRespDto getMyPage(Long userId) {
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("가입되지 않은 유저입니다.", HttpStatus.FORBIDDEN));

        Optional<Profile> profileOP = profileRepository.findByUserId(userPS.getId());
        if (profileOP.isEmpty()) {
            return new MyPageRespDto(userPS);
        }

        return new MyPageRespDto(userPS, profileOP.get());
    }

    // 마이페이지에서 본인이 수강중인 레슨내역보기
    @Transactional
    public MyLessonListRespDto getMyLesson(Long userId) {
        // 유저 검증
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("가입되지 않은 유저입니다.", HttpStatus.FORBIDDEN));

        // 일반 유저일 때
        if (userPS.getRole().getValue() == UserEnum.USER.getValue()) {
            // 구매 테이블에서 구매한 내역이 있는지 id로 조회 - 없으면 exception

            // 있으면 list로 출력
            return new MyLessonListRespDto(null);

            // 전문가일 때
        } else if (userPS.getRole().getValue() == UserEnum.EXPERT.getValue()) {
            // 레슨 테이블에서 생성한 레슨이 있는지 id로 조회 - 없으면 exception
            List<Lesson> lessonList = lessonRepository.findByUserId(userPS.getId());
            if (lessonList.size() == 0) {
                throw new CustomApiException("등록한 클래스가 없습니다.", HttpStatus.FORBIDDEN);
            }
            // 있으면 list로 출력
            return new MyLessonListRespDto(lessonList);
        } else {
            throw new CustomApiException("관리자 페이지에서 조회하세요.", HttpStatus.FORBIDDEN);
        }
    }

    public UserInitRespDto getInitSession(Long id) {
        User userPS = userRepository.findById(id)
                .orElseThrow(() -> new CustomApiException("유저 정보가 없습니다.", HttpStatus.BAD_REQUEST));
        return new UserInitRespDto(userPS);
    }

}
