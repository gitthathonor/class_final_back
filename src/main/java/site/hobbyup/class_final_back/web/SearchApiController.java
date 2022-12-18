package site.hobbyup.class_final_back.web;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

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
