package site.hobbyup.class_final_back.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
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
            InterestRepository interestRepository, LessonRepository lessonRepository, ReviewRepository reviewRepository,
            SubscribeRepository subscribeRepository, ExpertRepository expertRepository) {

        return (args) -> {
            User ssar = userRepository.save(newUser("ssar"));
            User cos = userRepository.save(newUser("cos"));
            User hong = userRepository.save(newUser("expert"));

            Expert expert = expertRepository.save(newExpert(hong));

            Category beauty = categoryRepository.save(newCategory("뷰티"));
            Category sports = categoryRepository.save(newCategory("스포츠"));
            Category dance = categoryRepository.save(newCategory("댄스"));
            Category music = categoryRepository.save(newCategory("음악"));
            Category art = categoryRepository.save(newCategory("미술"));
            Category crafts = categoryRepository.save(newCategory("공예"));
            Category game = categoryRepository.save(newCategory("게임"));
            Category others = categoryRepository.save(newCategory("기타"));

            Lesson lesson1 = lessonRepository.save(newLesson("더미1", 10000L, ssar, beauty));
            Lesson lesson2 = lessonRepository.save(newLesson("더미2", 20000L, ssar, sports));
            Lesson lesson3 = lessonRepository.save(newLesson("더미3", 50000L, ssar, music));
            Lesson lesson4 = lessonRepository.save(newLesson("더미4", 34500L, cos, music));
            Lesson lesson5 = lessonRepository.save(newLesson("더미5", 2400L, cos, music));
            Lesson lesson6 = lessonRepository.save(newLesson("더미6", 98000000L, cos, beauty));
            Lesson lesson7 = lessonRepository.save(newLesson("더미7", 30000L, ssar, sports));
            Lesson lesson8 = lessonRepository.save(newLesson("더미8", 40000L, ssar, sports));
            Lesson lesson9 = lessonRepository.save(newLesson("더미9", 50000L, ssar, sports));
            Lesson lesson10 = lessonRepository.save(newLesson("더미10", 70000L, ssar, sports));

            Review review1 = reviewRepository.save(newReivew("너무 좋은 강의입니다.", 4.5, ssar, lesson1));
            Review review2 = reviewRepository.save(newReivew("생각했던 것보다 더 좋네요!", 4.0, cos, lesson1));
            Review review3 = reviewRepository.save(newReivew("별로네요", 3.0, ssar, lesson2));
            Review review4 = reviewRepository.save(newReivew("도대체 이 강의 하시는 이유가 뭐죠?", 2.5, ssar, lesson3));
            Review review5 = reviewRepository.save(newReivew("피곤하다", 2.0, cos, lesson2));
            Review review6 = reviewRepository.save(newReivew("이정도 퀄리티면 좋은 거 같아요, 다만 목소리가...", 3.5, cos, lesson8));
            Review review7 = reviewRepository.save(newReivew("토큰 없음", 1.5, cos, lesson7));

            Subscribe subscribe1 = subscribeRepository.save(newSubscribe(ssar, lesson1));
            Subscribe subscribe2 = subscribeRepository.save(newSubscribe(ssar, lesson2));
            Subscribe subscribe3 = subscribeRepository.save(newSubscribe(cos, lesson3));
            Subscribe subscribe4 = subscribeRepository.save(newSubscribe(cos, lesson8));
            Subscribe subscribe5 = subscribeRepository.save(newSubscribe(cos, lesson9));

        };

    }
}
