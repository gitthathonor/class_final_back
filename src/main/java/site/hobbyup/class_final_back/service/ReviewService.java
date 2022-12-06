package site.hobbyup.class_final_back.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.exception.CustomApiException;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.review.ReviewRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;
import site.hobbyup.class_final_back.dto.review.ReviewReqDto.ReviewSaveReqDto;
import site.hobbyup.class_final_back.dto.review.ReviewRespDto.ReviewSaveRespDto;

@RequiredArgsConstructor
@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final LessonRepository lessonRepository;

  public ReviewSaveRespDto saveReview(ReviewSaveReqDto reviewSaveReqDto, Long userId, Long lessonId) {
    // 유저가 있는지 없는지
    User userPS = userRepository.findById(userId)
        .orElseThrow(() -> new CustomApiException("해당 유저가 없습니다.", HttpStatus.BAD_REQUEST));
    Lesson lessonPS = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new CustomApiException("해당 강의가 없습니다.", HttpStatus.BAD_REQUEST));

    // 현재 강의를 구입했는지 안 했는지 여부

    Review review = reviewSaveReqDto.toEntity(userPS, lessonPS);
    Review reviewPS = reviewRepository.save(review);
    return new ReviewSaveRespDto(reviewPS);
  }

}
