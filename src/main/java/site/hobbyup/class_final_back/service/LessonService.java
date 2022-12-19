package site.hobbyup.class_final_back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.lesson.LessonRepositoryQuery;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.review.ReviewRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.lesson.LessonCommonListDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonSaveReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonReqDto.LessonUpdateReqDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonCategoryListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonDetailRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSaveRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSearchListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonSubscribedListRespDto;
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonUpdateRespDto;
import site.hobbyup.class_final_back.util.DecodeUtil;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LessonService {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final LessonRepository lessonRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final ProfileRepository profileRepository;
  private final SubscribeRepository subscribeRepository;
  private final ExpertRepository expertRepository;
  private final LessonRepositoryQuery lessonRepositoryQuery;

  // 클래스 생성하기
  @Transactional
  public LessonSaveRespDto saveLesson(LessonSaveReqDto lessonSaveReqDto, LoginUser loginUser) throws IOException {

    // 이미지 파일 디코딩 후, 경로값 가져오기
    String photoPath = DecodeUtil.saveDecodingImage(lessonSaveReqDto.getPhoto());
    lessonSaveReqDto.setPhoto(photoPath);

    // 입력받은 카테고리 정보를 db의 정보와 비교해서 카테고리값 가져오기
    Category categoryPS = categoryRepository.findById(lessonSaveReqDto.getCategoryId())
        .orElseThrow(() -> new CustomApiException("해당하지 않는 카테고리입니다.", HttpStatus.BAD_REQUEST));

    // jwt에 담긴 user의 정보를 통해서 db에서 user데이터를 영속화 한 후에 RequestDto에 저장
    User userPS = userRepository.findById(loginUser.getUser().getId())
        .orElseThrow(() -> new CustomApiException("회원가입이 되지 않은 유저입니다.", HttpStatus.BAD_REQUEST));

    // user정보를 통해서 expert 정보를 영속화
    Expert expertPS = expertRepository.findByUserId(userPS.getId())
        .orElseThrow(() -> new CustomApiException("전문가 등록이 필요합니다.", HttpStatus.BAD_REQUEST));

    // 레슨 등록 권한이 true인지 확인
    if (!expertPS.isApproval()) {
      throw new CustomApiException("레슨 등록할 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

    // toEntity로 엔티티화 시킨 후에 저장하고 json(ResponseDto) 반환
    Lesson lessonPS = lessonRepository.save(lessonSaveReqDto.toEntity(categoryPS, expertPS));

    // possibleDays 파싱
    List<String> dayList = new ArrayList<>();
    if (lessonPS.getPossibleDays() == null) {
      dayList.add("");
    } else {
      String[] days = lessonPS.getPossibleDays().split(",");
      for (String day : days) {
        dayList.add(day);
      }
    }

    return new LessonSaveRespDto(lessonPS, dayList);
  }

  // 클래스 리스트 보기(카테고리별 + 예산별 필터링 적용)
  // public LessonCategoryListRespDto getLessonCategoryList(Long categoryId, Long
  // minPrice, Long maxPrice) {

  // // @PathVariable로 넘겨받은 categoryId를 통해서 카테고리를 영속화
  // Category categoryPS = categoryRepository.findById(categoryId)
  // .orElseThrow(() -> new CustomApiException("존재하지 않는 카테고리 입니다.",
  // HttpStatus.BAD_REQUEST));

  // // 영속화시킨 카테고리의 id로 where절을 걸어서 Lesson의 list를 반환
  // List<Lesson> lessonListPS =
  // lessonRepository.findByCategory(categoryPS.getId(), minPrice, maxPrice);

  // // 영속화시킨 lesson의
  // return new LessonCategoryListRespDto(categoryPS, lessonListPS);
  // }

  // 레슨 상세보기(로그인 시)
  @Transactional
  public LessonDetailRespDto getLessonDetail(Long lessonId, Long userId) {
    log.debug("디버그 : LessonService-getLessonDetail 실행");
    Lesson lessonPS = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new CustomApiException("해당 수업 없음", HttpStatus.BAD_REQUEST));

    // possibleDays 파싱
    List<String> dayList = new ArrayList<>();
    if (lessonPS.getPossibleDays() == null) {
      dayList.add("");
    } else {
      String[] days = lessonPS.getPossibleDays().split(",");
      for (String day : days) {
        dayList.add(day);
      }
    }

    // 프로필 정보 영속화
    Profile profilePS = profileRepository.findByUserId(lessonPS.getExpert().getUser().getId())
        .orElseThrow(() -> new CustomApiException("프로필을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));

    // 평균 리뷰 구하기 + 리뷰 리스트 뽑기
    List<Review> reviewListPS = reviewRepository.findAllByLessonId(lessonPS.getId());
    Double sum = 0.0;
    for (int i = 0; i < reviewListPS.size(); i++) {
      sum += reviewListPS.get(i).getGrade();
    }
    Double avgGrade = sum / reviewListPS.size();
    Integer totalReviews = reviewListPS.size();
    Long lessonTotalReviewsCount = totalReviews.longValue();

    // 찜 여부 확인하기
    boolean isSubscribed = false;
    Subscribe subscribePS = subscribeRepository.findByUserIdAndLessonId(userId, lessonId).orElse(null);
    if (subscribePS != null) {
      isSubscribed = true;
    }

    LessonDetailRespDto lessonDetailRespDto = new LessonDetailRespDto(lessonPS, dayList, profilePS, avgGrade,
        lessonTotalReviewsCount,
        isSubscribed,
        reviewListPS);
    return lessonDetailRespDto;
  }

  // 레슨 상세보기(비로그인 시)
  @Transactional
  public LessonDetailRespDto getLessonDetailNotLogin(Long lessonId) {
    log.debug("디버그 : LessonService-getLessonDetail 실행");
    Lesson lessonPS = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new CustomApiException("해당 수업 없음", HttpStatus.BAD_REQUEST));

    // possibleDays 파싱
    List<String> dayList = new ArrayList<>();
    if (lessonPS.getPossibleDays() == null) {
      dayList.add("");
    } else {
      String[] days = lessonPS.getPossibleDays().split(",");
      for (String day : days) {
        dayList.add(day);
      }
    }

    // 프로필 정보 영속화
    Profile profilePS = profileRepository.findByUserId(lessonPS.getExpert().getUser().getId())
        .orElseThrow(() -> new CustomApiException("프로필을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));

    // 평균 리뷰 구하기 + 리뷰 리스트 뽑기
    List<Review> reviewListPS = reviewRepository.findAllByLessonId(lessonPS.getId());
    Double sum = 0.0;
    for (int i = 0; i < reviewListPS.size(); i++) {
      sum += reviewListPS.get(i).getGrade();
    }
    Double avgGrade = sum / reviewListPS.size();
    Integer totalReviews = reviewListPS.size();
    Long lessonTotalReviewsCount = totalReviews.longValue();

    // 찜 여부 확인하기
    boolean isSubscribed = false;

    LessonDetailRespDto lessonDetailRespDto = new LessonDetailRespDto(lessonPS, dayList, profilePS, avgGrade,
        lessonTotalReviewsCount,
        isSubscribed,
        reviewListPS);
    return lessonDetailRespDto;
  }

  // 클래스 최신순 정렬
  // @Transactional
  // public LessonLatestListRespDto getLatestLessonList() {
  // List<Lesson> lessonList = lessonRepository.findAllLatest();
  // if (lessonList.size() == 0) {
  // throw new CustomApiException("게시글이 존재하지 않습니다.", HttpStatus.FORBIDDEN);
  // }

  // return new LessonLatestListRespDto(lessonList);
  // }

  // 클래스 삭제하기

  // 메인 페이지 보기
  public List<LessonCommonListDto> getLessonCommonList(Long userId) {
    return lessonRepository.findAllWithReview(userId);
  }

  // 비로그인 시 메인 페이지
  public List<LessonCommonListDto> getLessonCommonListNotLogin() {
    return lessonRepository.findAllWithReviewNotLogin();
  }

  // 레슨 수정하기
  public LessonUpdateRespDto updateLesson(LessonUpdateReqDto lessonUpdateReqDto, Long id, Long userId) {
    // 1. 이 레슨을 수정할 수 있는 권한이 있는지 확인
    User userPS = userRepository.findById(userId)
        .orElseThrow(() -> new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN));
    // 2. 레슨이 존재하는지 확인 후 영속화
    Lesson lessonPS = lessonRepository.findById(id)
        .orElseThrow(() -> new CustomApiException("해당 레슨이 없습니다.", HttpStatus.BAD_REQUEST));

    // 3. 카테고리 체크 후 영속화
    Category categoryPS = categoryRepository.findById(lessonUpdateReqDto.getCategoryId())
        .orElseThrow(() -> new CustomApiException("해당 카테고리가 없습니다.", HttpStatus.BAD_REQUEST));

    // 4. 더티체킹 후 수정
    lessonPS.update(lessonUpdateReqDto);
    lessonRepository.save(lessonPS);

    return new LessonUpdateRespDto(lessonPS);
  }

  // 카테고리별 레슨 리스트 보기(정렬 및 예산 필터까지 적용)(로그인 시)
  public List<LessonCategoryListRespDto> getLessonCategoryList(Long userId, Long categoryId, String sort, Long minPrice,
      Long maxPrice) {
    log.debug("디버그 : LessonService - getLessonCategoryList실행");
    // 회원 여부 체크
    User userPS = userRepository.findById(userId)
        .orElseThrow(() -> new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST));
    log.debug("디버그 : userPS : " + userPS.getUsername());
    List<LessonCategoryListRespDto> lessonCategoryListRespDtoList = lessonRepositoryQuery
        .findAllLessonCategoryListByUserId(
            userId,
            categoryId,
            sort, minPrice, maxPrice);
    if (lessonCategoryListRespDtoList.size() == 0) {
      throw new CustomApiException("조건에 맞는 서비스가 없습니다.", HttpStatus.BAD_REQUEST);
    }
    log.debug("디버그 : lessonCategoryListRespDtoList = " + lessonCategoryListRespDtoList.get(0).getLessonName());
    return lessonCategoryListRespDtoList;
  }

  // 카테고리별 레슨 리스트 보기(정렬 및 예산 필터까지 적용)(비로그인시)
  public List<LessonCategoryListRespDto> getLessonCategoryListNotLogin(Long categoryId, String sort,
      Long minPrice,
      Long maxPrice) {
    log.debug("디버그 : LessonService - getLessonCategoryListNotLogin실행");
    List<LessonCategoryListRespDto> lessonCategoryListRespDtoList = lessonRepositoryQuery.findAllLessonCategoryList(
        categoryId,
        sort, minPrice, maxPrice);
    if (lessonCategoryListRespDtoList.size() == 0) {
      throw new CustomApiException("조건에 맞는 서비스가 없습니다.", HttpStatus.BAD_REQUEST);
    }
    log.debug("디버그 : lessonCategoryListRespDtoList = " + lessonCategoryListRespDtoList.get(0).getLessonName());
    return lessonCategoryListRespDtoList;
  }

  // 검색, 로그인 시
  public List<LessonSearchListRespDto> getLessonListBySearch(Long userId, String keyword) {
    log.debug("디버그 : LessonService - getLessonListBySearch실행");
    List<LessonSearchListRespDto> lessonSearchListRespDtoList = lessonRepositoryQuery.findAllLessonByKeyword(userId,
        keyword);
    log.debug("디버그 : lessonSearchListRespDtoList = " + lessonSearchListRespDtoList.get(0).getLessonName());
    return lessonSearchListRespDtoList;
  }

  // 검색, 비로그인 시
  public List<LessonSearchListRespDto> getLessonListBySearchNotLogin(String keyword) {
    log.debug("디버그 : LessonService - getLessonListBySearchNotLogin실행");
    List<LessonSearchListRespDto> lessonSearchListRespDtoList = lessonRepositoryQuery
        .findAllLessonWithNotLoginByKeyword(keyword);
    log.debug("디버그 : lessonSearchListRespDtoList = " + lessonSearchListRespDtoList.get(0).getLessonName());
    return lessonSearchListRespDtoList;
  }

  // 찜한 클래스 목록보기
  public List<LessonSubscribedListRespDto> getLessonSubscribedList(Long userId) {
    log.debug("디버그 : LessonService - getLessonSubscribedList() 실행");
    List<LessonSubscribedListRespDto> lessonSubscribedListRespDtoList = lessonRepositoryQuery
        .findAllLessonBySubscribed(userId);
    return lessonSubscribedListRespDtoList;
  }

  // 전문가가 판매중인 레슨 리스트 보기
  // public ExpertSellingLessonListRespDto getSellingLessonList(Long userId) {
  // Expert expertPS = expertRepository.findByUserId(userId)
  // .orElseThrow(() -> new CustomApiException("전문가 등록이 필요합니다.",
  // HttpStatus.BAD_REQUEST));
  // ExpertSellingLessonListRespDto expertSellingLessonListRespDto =
  // expertRepository.findAllByExpertId();
  // return expertSellingLessonListRespDto;
  // }

}
