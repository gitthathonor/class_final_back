package site.hobbyup.class_final_back.dto.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.lesson.Lesson;

public class SearchRespDto {

    @Setter
    @Getter
    public static class SearchListRespDto {
        public List<SearchLessonRespDto> searchList = new ArrayList<>();

        public SearchListRespDto(List<Lesson> searchList) {
            this.searchList = searchList.stream().map(
                    (lesson) -> new SearchLessonRespDto(lesson)).collect(Collectors.toList());
        }

        @Setter
        @Getter
        public static class SearchLessonRespDto {
            private Long id;
            private String name;
            private String photo;
            private Long price;

            public SearchLessonRespDto(Lesson lesson) {
                this.id = lesson.getId();
                this.name = lesson.getName();
                this.photo = lesson.getPhoto();
                this.price = lesson.getPrice();
            }
        }
    }
}
