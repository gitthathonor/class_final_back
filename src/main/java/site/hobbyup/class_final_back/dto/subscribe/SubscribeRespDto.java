package site.hobbyup.class_final_back.dto.subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transaction;

import org.springframework.transaction.TransactionDefinition;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.review.Review;
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

    @Setter
    @Getter
    public static class SubscribeListRespDto {
        private List<SubscribeDto> subscribes = new ArrayList<>();

        public SubscribeListRespDto(List<Subscribe> subscribes) {
            this.subscribes = subscribes.stream().map(
                    (subscribe) -> new SubscribeDto(subscribe)).collect(Collectors.toList());
        }

        @Setter
        @Getter
        public static class SubscribeDto {
            private Long id;
            private UserDto user;
            private LessonDto lesson;

            public SubscribeDto(Subscribe subscribe) {
                this.id = subscribe.getId();
                this.user = new UserDto(subscribe.getUser());
                this.lesson = new LessonDto(subscribe.getLesson());
            }

            @Setter
            @Getter
            public static class UserDto {
                private String username;

                public UserDto(User user) {
                    this.username = user.getUsername();
                }
            }

            @Setter
            @Getter
            public static class LessonDto {
                private Long id;
                private String name;
                private String photo;
                private Long price;
                // private ReviewDto review;

                public LessonDto(Lesson lesson) {
                    this.id = lesson.getId();
                    this.name = lesson.getName();
                    this.photo = lesson.getPhoto();
                    this.price = lesson.getPrice();
                    // this.review = new ReviewDto(lesson.getReview());

                }

                // @Getter
                // public class ReviewDto {
                // Long id;
                // Double grade;

                // public ReviewDto(Review review) {
                // this.id = review.getId();
                // this.grade = review.getGrade();
                // }
                // }
            }
        }
    }
}
