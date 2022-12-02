package site.hobbyup.class_final_back.dto.subscribe;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.user.User;

public class SubscribeRespDto {

    @Setter
    @Getter
    public static class SubscribeSaveRespDto {
        private Long id;
        private UserDto user;
        private LessonDto lesson;

        @Setter
        @Getter
        public class UserDto {
            private Long id;

            public UserDto(User user) {
                this.id = user.getId();
            }
        }

        @Setter
        @Getter
        public class LessonDto {
            private Long id;

            public LessonDto(Lesson lesson) {
                this.id = lesson.getId();
            }
        }

        public SubscribeSaveRespDto(Subscribe subscribe) {
            this.id = subscribe.getId();
            this.user = new UserDto(subscribe.getUser());
            this.lesson = new LessonDto(subscribe.getLesson());
        }

    }

    @Setter
    @Getter
    public static class SubscribeDeleteRespDto {
        private Long id;
        private UserDto user;
        private LessonDto lesson;

        public SubscribeDeleteRespDto(Subscribe subscribe) {
            this.id = subscribe.getId();
            this.user = new UserDto(subscribe.getUser());
            this.lesson = new LessonDto(subscribe.getLesson());
        }

        @Setter
        @Getter
        public class UserDto {
            private Long id;

            public UserDto(User user) {
                this.id = user.getId();
            }
        }

        @Setter
        @Getter
        public class LessonDto {
            private Long id;

            public LessonDto(Lesson lesson) {
                this.id = lesson.getId();
            }
        }
    }
}
