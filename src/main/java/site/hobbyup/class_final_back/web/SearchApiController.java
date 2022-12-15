package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.search.SearchRespDto.SearchListRespDto;
import site.hobbyup.class_final_back.service.SearchService;

@RequiredArgsConstructor
@RestController
public class SearchApiController {
    // private final Logger log = LoggerFactory.getLogger(getClass());
    // private final SearchService searchService;

    // // 인증 필요하게 할건지(로그인여부확인)
    // @GetMapping("/api/search")
    // public ResponseEntity<?> getSearchClass(@RequestParam(value = "keyword")
    // String keyword) {
    // SearchListRespDto searchListRespDto = searchService.getSearchClass(keyword);
    // return new ResponseEntity<>(new ResponseDto<>("검색하기", searchListRespDto),
    // HttpStatus.CREATED);
    // }
}
