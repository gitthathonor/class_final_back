package site.hobbyup.class_final_back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.dto.search.SearchRespDto.SearchListRespDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LessonRepository lessonRepository;

    @Transactional
    public SearchListRespDto searchClass(String keyword) {
        List<Lesson> lessonList = lessonRepository.findByKeyword(keyword);
        return new SearchListRespDto(lessonList);
    }

}
