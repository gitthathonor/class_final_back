package site.hobbyup.class_final_back.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.interest.InterestRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.review.ReviewRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;

@RequiredArgsConstructor
@Configuration
public class DevInit extends DummyEntity {

    @Profile("dev")
    @Bean
    public CommandLineRunner dataSetting(UserRepository userRepository, CategoryRepository categoryRepository,
            InterestRepository interestRepository, LessonRepository lessonRepository,
            SubscribeRepository subscribeRepository, ReviewRepository reviewRepository) {

        return (args) -> {
            User ssar = userRepository.save(newUser("ssar"));
            User cos = userRepository.save(newUser("cos"));
            User aa = userRepository.save(newUser("aa"));

            Category beauty = categoryRepository.save(newCategory("뷰티"));
            Category sports = categoryRepository.save(newCategory("스포츠"));
            Category dance = categoryRepository.save(newCategory("댄스"));
            Category music = categoryRepository.save(newCategory("음악"));
            Category art = categoryRepository.save(newCategory("미술"));
            Category crafts = categoryRepository.save(newCategory("공예"));
            Category game = categoryRepository.save(newCategory("게임"));
            Category others = categoryRepository.save(newCategory("기타"));

            Lesson lesson = lessonRepository.save(newLesson("더미1", 10000L, ssar, beauty));
            Lesson lesson2 = lessonRepository.save(newLesson("더미2", 20000L, ssar, sports));
            Lesson lesson3 = lessonRepository.save(newLesson("더미3", 50000L, ssar, music));
            Lesson lesson4 = lessonRepository.save(newLesson("더미4", 34500L, cos, music));
            Lesson lesson5 = lessonRepository.save(newLesson("더미5", 2400L, cos, music));
            Lesson lesson6 = lessonRepository.save(newLesson("더미6", 98000000L, cos, beauty));
            Lesson lesson7 = lessonRepository.save(newLesson("더미7", 30000L, ssar, sports));
            Lesson lesson8 = lessonRepository.save(newLesson("더미8", 40000L, ssar, sports));
            Lesson lesson9 = lessonRepository.save(newLesson("더미9", 50000L, ssar, sports));
            Lesson lesson10 = lessonRepository.save(newLesson("더미10", 70000L, ssar, sports));

            Subscribe subscribe1 = subscribeRepository.save(newSubscribe(ssar, lesson10));
            Subscribe subscribe2 = subscribeRepository.save(newSubscribe(cos, lesson10));
            Subscribe subscribe3 = subscribeRepository.save(newSubscribe(aa, lesson10));
            Subscribe subscribe4 = subscribeRepository.save(newSubscribe(aa, lesson9));
            Subscribe subscribe5 = subscribeRepository.save(newSubscribe(cos, lesson9));
            Subscribe subscribe6 = subscribeRepository.save(newSubscribe(ssar, lesson8));

            Review review1 = reviewRepository.save(newReivew("리뷰1", 2.5, ssar, lesson));
            Review review2 = reviewRepository.save(newReivew("리뷰1", 2.5, ssar, lesson));
            Review review3 = reviewRepository.save(newReivew("리뷰1", 2.5, ssar, lesson));
            Review review4 = reviewRepository.save(newReivew("리뷰1", 2.5, ssar, lesson));

        };

    }
}
