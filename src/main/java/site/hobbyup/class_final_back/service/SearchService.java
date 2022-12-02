package site.hobbyup.class_final_back.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.util.DecodeUtil;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchService extends DecodeUtil {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LessonRepository lessonRepository;

    @Transactional
    public void searchClass(String keyword) {
    }
}
