package site.hobbyup.class_final_back.dto.subscribe;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.user.User;

public class SubscribeReqDto {

    @Setter
    @Getter
    public static class SubscribeSaveReqDto {
        private Lesson lesson;

        public Subscribe toEntity(User user) {
            return Subscribe.builder()
                    .user(user)
                    .lesson(lesson)
                    .build();
        }

    }
}
