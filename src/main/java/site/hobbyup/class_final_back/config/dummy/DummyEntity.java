package site.hobbyup.class_final_back.config.dummy;

import java.sql.Timestamp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.coupon.Coupon;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.user.User;

public class DummyEntity {
    protected User newUser(String username) {
        Long phoneNum = 1L;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        User user = User.builder()
                .username(username)
                .password(encPassword)
                .email(username + "@nate.com")
                .phoneNum("010" + (++phoneNum))
                .role(username.equals("admin") ? UserEnum.ADMIN : UserEnum.USER)
                .build();
        return user;
    }

    protected Profile newProfile(String filePath, String introduction, String region, String certification,
            String careerYear, String career, User user) {
        Profile profile = Profile.builder()
                .filePath(filePath)
                .introduction(introduction)
                .region(region)
                .certification(certification)
                .careerYear(careerYear)
                .career(career)
                .user(user)
                .build();
        return profile;
    }

    protected Category newCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .build();
        return category;
    }

    protected Lesson newLesson(String dummy, Long dummyNum, User user, Category category) {
        return Lesson.builder()
                .name(dummy)
                .photo(dummy)
                .price(dummyNum)
                .place(dummy)
                .user(user)
                .curriculum("커리큘럼" + dummy)
                .expiredAt(new Timestamp(50000))
                .policy("취소 환불 정책" + dummy)
                .category(category)
                .build();
    }

    protected Review newReivew(String text, Double grade, User user, Lesson lesson) {
        return Review.builder()
                .content(text)
                .grade(grade)
                .user(user)
                .lesson(lesson)
                .build();
    }

    protected Subscribe newSubscribe(User user, Lesson lesson) {
        Subscribe subscribe = Subscribe.builder()
                .user(user)
                .lesson(lesson)
                .build();
        return subscribe;
    }

    protected Coupon newCoupon(String title, Long price, Timestamp expiredDate, Timestamp createdAt, User user) {
        Coupon coupon = Coupon.builder()
                .title(title)
                .price(price)
                .expiredDate(expiredDate)
                .createdAt(createdAt)
                .user(user)
                .build();
        return coupon;
    }

}