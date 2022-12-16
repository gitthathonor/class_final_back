package site.hobbyup.class_final_back.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.review.ReviewReqDto.ReviewSaveReqDto;
import site.hobbyup.class_final_back.dto.review.ReviewRespDto.ReviewSaveRespDto;
import site.hobbyup.class_final_back.service.ReviewService;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {

  private final ReviewService reviewService;

  @PostMapping("/api/user/{userId}/lesson/{lessonId}/review")
  public ResponseEntity<?> saverReview(@RequestBody ReviewSaveReqDto reviewSaveReqDto,
      @PathVariable("userId") Long userId, @PathVariable("lessonId") Long lessonId) {
    ReviewSaveRespDto reviewSaveRespDto = reviewService.saveReview(reviewSaveReqDto, userId, lessonId);
    return new ResponseEntity<>(new ResponseDto<>("리뷰작성성공", reviewSaveRespDto), HttpStatus.CREATED);
  }

  @GetMapping("/api/review/test")
  public ResponseEntity<?> getReviews() {
    return new ResponseEntity<>(new ResponseDto<>("리뷰 리스트 불러오기 성공", reviewService.getReviews()), HttpStatus.OK);
  }
}
