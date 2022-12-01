package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.service.CategoryService;

@RequiredArgsConstructor
@RestController
public class CategoryApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CategoryService categoryService;

}
