package site.hobbyup.class_final_back.dto.user;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.interest.Interest;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.user.User;

public class UserRespDto {

    @Getter
    @Setter
    public static class JoinRespDto {
        private Long id;
        private String username;
        private boolean isDisabled;
        private List<InterestDto> interestList;

        public JoinRespDto(User user, List<Interest> interestList) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.isDisabled = user.isDisabled();
            this.interestList = interestList.stream().map((interest) -> new InterestDto(interest))
                    .collect(Collectors.toList());
        }

        @Setter
        @Getter
        public class InterestDto {
            private String categoryName;

            public InterestDto(Interest interest) {
                this.categoryName = interest.getCategory().getName();
            }
        }
    }

    @Setter
    @Getter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String role;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole().getValue();
            this.createdAt = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    @Setter
    @Getter
    public static class UserUpdateRespDto {
        private Long id;
        private String username;
        private String email;
        private String phoneNum;

        public UserUpdateRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.phoneNum = user.getPhoneNum();
        }

    }

    @Setter
    @Getter
    public static class MyPageRespDto {
        private Long id;
        private String username;
        private UserEnum role;
        private String filePath;

        public MyPageRespDto(User user, Profile profile) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.filePath = profile.getFilePath();
        }
    }

    @Setter
    @Getter
    public static class MyLessonListRespDto {
        List<MyLessonRespDto> lessonList = new ArrayList<>();

        public MyLessonListRespDto(List<Lesson> lessonList) {
            this.lessonList = lessonList.stream().map((lesson) -> new MyLessonRespDto(lesson))
                    .collect(Collectors.toList());
        }

        @Setter
        @Getter
        public static class MyLessonRespDto {
            private String name;
            private UserDto user;
            private Long price;
            private Timestamp expiredAt;
            private String photo;

            public MyLessonRespDto(Lesson lesson) {
                this.name = lesson.getName();
                this.user = new UserDto(lesson.getUser());
                this.price = lesson.getPrice();
                this.expiredAt = lesson.getExpiredAt();
                this.photo = lesson.getPhoto();
            }

            @Setter
            @Getter
            public static class UserDto {
                private String username;

                public UserDto(User user) {
                    this.username = user.getUsername();
                }
            }
        }
    }

    @Setter
    @Getter
    public static class UserDeleteRespDto {
        private String username;
        private boolean isDisabled;

        public UserDeleteRespDto(User user) {
            this.username = user.getUsername();
            this.isDisabled = user.isDisabled();
        }

    }
}
