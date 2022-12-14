package site.hobbyup.class_final_back.domain.lesson;

import java.util.List;

import site.hobbyup.class_final_back.dto.lesson.LessonSortListRespDto;

interface Dao {
  List<LessonSortListRespDto> findAllBySort(Long userId, Long categoryId, String sorting);
}

public class LessonRepositoryImpl implements Dao {

  @Override
  public List<LessonSortListRespDto> findAllBySort(Long userId, Long categoryId, String sorting) {

    return null;
  }

}
