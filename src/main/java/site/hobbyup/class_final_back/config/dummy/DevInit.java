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
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;

@RequiredArgsConstructor
@Configuration
public class DevInit extends DummyEntity {

    @Profile("dev")
    @Bean
    public CommandLineRunner dataSetting(UserRepository userRepository, CategoryRepository categoryRepository,
            InterestRepository interestRepository, LessonRepository lessonRepository) {

        return (args) -> {
            User ssar = userRepository.save(newUser("ssar"));

            Category beauty = categoryRepository.save(newCategory("뷰티"));
            Category sports = categoryRepository.save(newCategory("스포츠"));
            Category dance = categoryRepository.save(newCategory("댄스"));
            Category music = categoryRepository.save(newCategory("음악"));
            Category art = categoryRepository.save(newCategory("미술"));
            Category crafts = categoryRepository.save(newCategory("공예"));
            Category game = categoryRepository.save(newCategory("게임"));
            Category others = categoryRepository.save(newCategory("기타"));

            Lesson lesson = lessonRepository.save(newLesson("더미1", 10000L, ssar, beauty));

        };

    }
}
