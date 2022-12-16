package site.hobbyup.class_final_back.dto.user;

import java.sql.Timestamp;
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
import site.hobbyup.class_final_back.dto.lesson.LessonRespDto.LessonDetailRespDto.ProfileDto;

public class UserRespDto {

    @Getter
    @Setter
    public static class JoinRespDto {
        private Long id;
        private String username;
        private boolean isDisabled;
        private boolean isDisabled;
        private List<InterestDto> interestList;

        public JoinRespDto(User user, List<Interest> interestList) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.isDisabled = user.isDisabled();
            this.isDisabled = user.isDisabled();
            this.interestList = interestList.stream().map((interest) -> new InterestDto(interest))
                    .collect(Collectors.toList());
        }

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.isDisabled = user.isDisabled();
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
        private String role;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole().getValue();
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
        private List<InterestDto> interestList;

        public UserUpdateRespDto(User user, List<Interest> interestList) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.phoneNum = user.getPhoneNum();
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
    public static class MyPageRespDto {
        private Long id;
        private String username;
        private UserEnum role;
        private ProfileDto profileDto;

        public MyPageRespDto(User user, Profile profile) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.profileDto = new ProfileDto(profile);
        }

        public MyPageRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
        }

        @Setter
        @Getter
        public class ProfileDto {
            private Long id;
            private String profilePhoto;

            public ProfileDto(Profile profile) {
                this.id = profile.getId();
                this.profilePhoto = profile.getFilePath();
            }

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
            private Timestamp deadline;
            private String photo;

            public MyLessonRespDto(Lesson lesson) {
                this.name = lesson.getName();
                this.user = new UserDto(lesson.getExpert().getUser());
                this.price = lesson.getPrice();
                this.deadline = lesson.getDeadline();
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

    @Setter
    @Getter
    public static class UserInitRespDto {
        private Long id;
        private String username;
        private String password;
        private String phoneNum;
        private String email;
        private String role;

        private String createdAt;

        public UserInitRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.phoneNum = user.getPhoneNum();
            this.email = user.getEmail();
            this.role = user.getRole().getValue();
            this.createdAt = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

    }

}
