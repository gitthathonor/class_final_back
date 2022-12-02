package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.service.LessonService;
import site.hobbyup.class_final_back.service.SearchService;

@RequiredArgsConstructor
@RestController
public class SearchApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SearchService searchService;

    @GetMapping("/api/class")
    public String searchClass(@RequestParam(value = "keyword") String keyword) {
        searchService.searchClass(keyword);
        return "";
    }
}
