package site.hobbyup.class_final_back.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.domain.claim.Claim;
import site.hobbyup.class_final_back.domain.claim.ClaimRepository;
import site.hobbyup.class_final_back.domain.coupon.Coupon;
import site.hobbyup.class_final_back.domain.coupon.CouponRepository;
import site.hobbyup.class_final_back.domain.expert.Expert;
import site.hobbyup.class_final_back.domain.expert.ExpertRepository;
import site.hobbyup.class_final_back.domain.interest.Interest;
import site.hobbyup.class_final_back.domain.interest.InterestRepository;
import site.hobbyup.class_final_back.domain.lesson.Lesson;
import site.hobbyup.class_final_back.domain.lesson.LessonRepository;
import site.hobbyup.class_final_back.domain.payment.Payment;
import site.hobbyup.class_final_back.domain.payment.PaymentRepository;
import site.hobbyup.class_final_back.domain.paymentType.PaymentType;
import site.hobbyup.class_final_back.domain.paymentType.PaymentTypeRepository;
import site.hobbyup.class_final_back.domain.profile.Profile;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;
import site.hobbyup.class_final_back.domain.review.Review;
import site.hobbyup.class_final_back.domain.review.ReviewRepository;
import site.hobbyup.class_final_back.domain.subscribe.Subscribe;
import site.hobbyup.class_final_back.domain.subscribe.SubscribeRepository;
import site.hobbyup.class_final_back.domain.user.User;
import site.hobbyup.class_final_back.domain.user.UserRepository;

@RequiredArgsConstructor
@Configuration
public class DevInit extends DummyEntity {

        @org.springframework.context.annotation.Profile("dev")
        @Bean
        public CommandLineRunner dataSetting(UserRepository userRepository, CategoryRepository categoryRepository,
                        InterestRepository interestRepository, LessonRepository lessonRepository,
                        ReviewRepository reviewRepository,
                        SubscribeRepository subscribeRepository, ExpertRepository expertRepository,
                        ProfileRepository profileRepository, PaymentTypeRepository paymentTypeRepository,
                        CouponRepository couponRepository, PaymentRepository paymentRepository,
                        ClaimRepository claimRepository) {

                return (args) -> {
                        Category beauty = categoryRepository.save(newCategory("??????"));
                        Category sports = categoryRepository.save(newCategory("?????????"));
                        Category dance = categoryRepository.save(newCategory("??????"));
                        Category music = categoryRepository.save(newCategory("??????"));
                        Category art = categoryRepository.save(newCategory("??????"));
                        Category crafts = categoryRepository.save(newCategory("??????"));
                        Category game = categoryRepository.save(newCategory("??????"));
                        Category others = categoryRepository.save(newCategory("??????"));

                        PaymentType card = paymentTypeRepository.save(newPaymentType("????????????"));
                        PaymentType vBank = paymentTypeRepository.save(newPaymentType("???????????????"));
                        PaymentType kakaoPay = paymentTypeRepository.save(newPaymentType("???????????????"));

                        User ssar = userRepository.save(newUser("ssar", UserEnum.USER));
                        User cos = userRepository.save(newUser("cos", UserEnum.USER));
                        User aa = userRepository.save(newUser("aa", UserEnum.USER));
                        User hong = userRepository.save(newUser("hong", UserEnum.EXPERT));
                        User lala = userRepository.save(newUser("lala", UserEnum.EXPERT));

                        Expert expert1 = expertRepository.save(newExpert(hong));
                        Expert expert2 = expertRepository.save(newExpert(lala));

                        Profile profile1 = profileRepository
                                        .save(newProfile("", "ssar??? ??????????????????.", "????????????", "ssar??? ??????????????????.",
                                                        "5???", "????????? ???????????? 10?????? ???????????? ??????", ssar));
                        Profile profile2 = profileRepository.save(newProfile("", "cos??? ??????????????????.", "????????????", "cos??? ??????????????????.",
                                        "5???", "????????? ???????????? 10?????? ???????????? ??????", cos));
                        Profile profile3 = profileRepository.save(newProfile("", "aa??? ??????????????????.", "????????????", "aa??? ??????????????????.",
                                        "5???", "????????? ???????????? 10?????? ???????????? ??????", aa));
                        Profile profile4 = profileRepository
                                        .save(newProfile("", "hong??? ??????????????????.", "????????????", "hong??? ??????????????????.",
                                                        "5???", "????????? ???????????? 10?????? ???????????? ??????", hong));
                        Profile profile5 = profileRepository
                                        .save(newProfile("", "lala??? ??????????????????.", "????????????", "lala??? ??????????????????.",
                                                        "5???", "????????? ???????????? 10?????? ???????????? ??????", lala));

                        Lesson lesson1 = lessonRepository.save(newLesson("??????1", 10000L, expert1, beauty));
                        Lesson lesson2 = lessonRepository.save(newLesson("??????2", 50000L, expert1, beauty));
                        Lesson lesson3 = lessonRepository.save(newLesson("??????3", 15000L, expert1, beauty));
                        Lesson lesson4 = lessonRepository.save(newLesson("??????4", 50000L, expert1, sports));
                        Lesson lesson5 = lessonRepository.save(newLesson("??????5", 200000L, expert1, sports));
                        Lesson lesson6 = lessonRepository.save(newLesson("??????6", 12000L, expert1, sports));
                        Lesson lesson7 = lessonRepository.save(newLesson("??????7", 5000L, expert1, dance));
                        Lesson lesson8 = lessonRepository.save(newLesson("??????8", 70000L, expert1, dance));
                        Lesson lesson9 = lessonRepository.save(newLesson("??????9", 20000L, expert1, dance));
                        Lesson lesson10 = lessonRepository.save(newLesson("??????10", 345000L, expert1, music));
                        Lesson lesson11 = lessonRepository.save(newLesson("??????11", 400000L, expert1, music));
                        Lesson lesson12 = lessonRepository.save(newLesson("??????12", 10000L, expert1, music));
                        Lesson lesson13 = lessonRepository.save(newLesson("??????13", 240000L, expert1, art));
                        Lesson lesson14 = lessonRepository.save(newLesson("??????14", 5000L, expert1, art));
                        Lesson lesson15 = lessonRepository.save(newLesson("??????15", 70000L, expert1, art));
                        Lesson lesson16 = lessonRepository.save(newLesson("??????16", 98000000L, expert1, crafts));
                        Lesson lesson17 = lessonRepository.save(newLesson("??????17", 40000L, expert1, crafts));
                        Lesson lesson18 = lessonRepository.save(newLesson("??????18", 150000L, expert1, crafts));
                        Lesson lesson19 = lessonRepository.save(newLesson("??????19", 30000L, expert1, game));
                        Lesson lesson20 = lessonRepository.save(newLesson("??????20", 80000L, expert1, game));
                        Lesson lesson21 = lessonRepository.save(newLesson("??????21", 5000L, expert1, game));
                        Lesson lesson22 = lessonRepository.save(newLesson("??????22", 40000L, expert1, others));
                        Lesson lesson23 = lessonRepository.save(newLesson("??????23", 100000L, expert1, others));
                        Lesson lesson24 = lessonRepository.save(newLesson("??????24", 75000L, expert1, others));
                        Lesson lesson25 = lessonRepository.save(newLesson("??????25", 50000L, expert2, beauty));
                        Lesson lesson26 = lessonRepository.save(newLesson("??????26", 150000L, expert2, beauty));
                        Lesson lesson27 = lessonRepository.save(newLesson("??????27", 80000L, expert2, beauty));
                        Lesson lesson28 = lessonRepository.save(newLesson("??????28", 70000L, expert2, sports));
                        Lesson lesson29 = lessonRepository.save(newLesson("??????29", 55000L, expert2, sports));
                        Lesson lesson30 = lessonRepository.save(newLesson("??????30", 22000L, expert2, sports));
                        Lesson lesson31 = lessonRepository.save(newLesson("??????31", 50000L, expert2, dance));
                        Lesson lesson32 = lessonRepository.save(newLesson("??????32", 15000L, expert2, dance));
                        Lesson lesson33 = lessonRepository.save(newLesson("??????33", 65000L, expert2, dance));
                        Lesson lesson34 = lessonRepository.save(newLesson("??????34", 70000L, expert2, music));
                        Lesson lesson35 = lessonRepository.save(newLesson("??????35", 55000L, expert2, music));
                        Lesson lesson36 = lessonRepository.save(newLesson("??????36", 100000L, expert2, music));
                        Lesson lesson37 = lessonRepository.save(newLesson("??????37", 50000L, expert2, art));
                        Lesson lesson38 = lessonRepository.save(newLesson("??????38", 200000L, expert2, art));
                        Lesson lesson39 = lessonRepository.save(newLesson("??????39", 85000L, expert2, art));
                        Lesson lesson40 = lessonRepository.save(newLesson("??????40", 70000L, expert2, crafts));
                        Lesson lesson41 = lessonRepository.save(newLesson("??????41", 80000L, expert2, crafts));
                        Lesson lesson42 = lessonRepository.save(newLesson("??????42", 1000000L, expert2, crafts));
                        Lesson lesson43 = lessonRepository.save(newLesson("??????43", 50000L, expert2, game));
                        Lesson lesson44 = lessonRepository.save(newLesson("??????44", 55000L, expert2, game));
                        Lesson lesson45 = lessonRepository.save(newLesson("??????45", 200000L, expert2, game));
                        Lesson lesson46 = lessonRepository.save(newLesson("??????46", 70000L, expert2, others));
                        Lesson lesson47 = lessonRepository.save(newLesson("??????47", 250000L, expert2, others));
                        Lesson lesson48 = lessonRepository.save(newLesson("??????48", 10000L, expert2, others));

                        Review review1 = reviewRepository.save(newReivew("?????? ?????? ???????????????.", 4.5, ssar, lesson1));
                        Review review2 = reviewRepository.save(newReivew("???????????? ????????? ??? ?????????!", 4.0, cos, lesson1));
                        Review review3 = reviewRepository.save(newReivew("????????????", 3.0, ssar, lesson2));
                        Review review4 = reviewRepository.save(newReivew("????????????", 2.0, cos, lesson2));
                        Review review5 = reviewRepository.save(newReivew("????????? ??? ?????? ????????? ????????? ???????", 2.5, lala, lesson3));
                        Review review6 = reviewRepository
                                        .save(newReivew("????????? ???????????? ?????? ??? ?????????, ?????? ????????????...", 3.5, cos, lesson4));
                        Review review7 = reviewRepository.save(newReivew("?????? ?????? ???????????????.", 4.5, hong, lesson5));
                        Review review8 = reviewRepository.save(newReivew("???????????? ????????? ??? ?????????!", 4.0, aa, lesson8));
                        Review review9 = reviewRepository.save(newReivew("????????????", 3.0, lala, lesson11));
                        Review review10 = reviewRepository.save(newReivew("????????????", 2.0, ssar, lesson15));
                        Review review11 = reviewRepository.save(newReivew("????????? ??? ?????? ????????? ????????? ???????", 1.5, cos, lesson15));
                        Review review12 = reviewRepository
                                        .save(newReivew("????????? ???????????? ?????? ??? ?????????, ?????? ????????????...", 3.5, hong, lesson15));
                        Review review13 = reviewRepository.save(newReivew("?????? ?????? ???????????????.", 4.5, ssar, lesson19));
                        Review review14 = reviewRepository.save(newReivew("???????????? ????????? ??? ?????????!", 4.0, lala, lesson19));
                        Review review15 = reviewRepository.save(newReivew("????????????", 3.0, hong, lesson22));
                        Review review16 = reviewRepository.save(newReivew("????????????", 2.0, aa, lesson25));
                        Review review17 = reviewRepository.save(newReivew("????????? ??? ?????? ????????? ????????? ???????", 2.5, ssar, lesson30));
                        Review review18 = reviewRepository
                                        .save(newReivew("????????? ???????????? ?????? ??? ?????????, ?????? ????????????...", 3.5, cos, lesson32));
                        Review review19 = reviewRepository.save(newReivew("?????? ?????? ???????????????.", 4.5, aa, lesson32));
                        Review review20 = reviewRepository.save(newReivew("???????????? ????????? ??? ?????????!", 4.0, lala, lesson39));
                        Review review21 = reviewRepository.save(newReivew("????????????", 3.0, ssar, lesson40));
                        Review review22 = reviewRepository.save(newReivew("????????????", 2.0, hong, lesson42));
                        Review review23 = reviewRepository.save(newReivew("????????? ??? ?????? ????????? ????????? ???????", 2.5, ssar, lesson43));
                        Review review24 = reviewRepository
                                        .save(newReivew("????????? ???????????? ?????? ??? ?????????, ?????? ????????????...", 3.5, cos, lesson48));

                        Subscribe subscribe1 = subscribeRepository.save(newSubscribe(ssar, lesson1));
                        Subscribe subscribe2 = subscribeRepository.save(newSubscribe(ssar, lesson25));
                        Subscribe subscribe3 = subscribeRepository.save(newSubscribe(cos, lesson3));
                        Subscribe subscribe4 = subscribeRepository.save(newSubscribe(cos, lesson18));
                        Subscribe subscribe5 = subscribeRepository.save(newSubscribe(cos, lesson39));
                        Subscribe subscribe6 = subscribeRepository.save(newSubscribe(aa, lesson15));
                        Subscribe subscribe7 = subscribeRepository.save(newSubscribe(aa, lesson20));
                        Subscribe subscribe8 = subscribeRepository.save(newSubscribe(aa, lesson29));
                        Subscribe subscribe9 = subscribeRepository.save(newSubscribe(aa, lesson39));
                        Subscribe subscribe10 = subscribeRepository.save(newSubscribe(aa, lesson48));
                        Subscribe subscribe11 = subscribeRepository.save(newSubscribe(hong, lesson10));
                        Subscribe subscribe12 = subscribeRepository.save(newSubscribe(hong, lesson20));
                        Subscribe subscribe13 = subscribeRepository.save(newSubscribe(hong, lesson22));
                        Subscribe subscribe14 = subscribeRepository.save(newSubscribe(hong, lesson29));
                        Subscribe subscribe15 = subscribeRepository.save(newSubscribe(lala, lesson1));
                        Subscribe subscribe16 = subscribeRepository.save(newSubscribe(lala, lesson35));

                        Coupon coupon1 = couponRepository.save(newCoupon("???????????? ??????", 10000L, "2022-12-22", ssar));
                        Coupon coupon2 = couponRepository.save(newCoupon("???????????? ??????", 10000L, "2022-12-22", cos));
                        Coupon coupon3 = couponRepository.save(newCoupon("???????????? ??????", 10000L, "2022-12-22", aa));
                        Coupon coupon4 = couponRepository.save(newCoupon("???????????? ??????", 10000L, "2022-12-22", hong));
                        Coupon coupon5 = couponRepository.save(newCoupon("???????????? ??????", 10000L, "2022-12-22", lala));

                        Payment ssarPayment1 = paymentRepository.save(newPayment(ssar, lesson1, card, coupon1, 2));
                        // Payment ssarPayment2 = paymentRepository.save(newPayment(ssar, lesson12,
                        // kakaoPay, null, 1));
                        // Payment ssarPayment3 = paymentRepository.save(newPayment(ssar, lesson17,
                        // card, null, 2));
                        // Payment ssarPayment4 = paymentRepository.save(newPayment(ssar, lesson20,
                        // vBank, null, 3));
                        // Payment ssarPayment5 = paymentRepository.save(newPayment(ssar, lesson30,
                        // vBank, null, 1));
                        // Payment ssarPayment6 = paymentRepository.save(newPayment(cos, lesson5, card,
                        // coupon1, 2));
                        // Payment ssarPayment7 = paymentRepository.save(newPayment(cos, lesson10,
                        // kakaoPay, null, 1));
                        // Payment ssarPayment8 = paymentRepository.save(newPayment(cos, lesson17, card,
                        // null, 2));
                        // Payment ssarPayment9 = paymentRepository.save(newPayment(cos, lesson32,
                        // vBank, null, 3));
                        // Payment ssarPayment10 = paymentRepository.save(newPayment(cos, lesson45,
                        // vBank, null, 1));
                        // Payment ssarPayment11 = paymentRepository.save(newPayment(aa, lesson7, card,
                        // coupon1, 2));
                        // Payment ssarPayment12 = paymentRepository.save(newPayment(aa, lesson15,
                        // kakaoPay, null, 1));
                        // Payment ssarPayment13 = paymentRepository.save(newPayment(aa, lesson27, card,
                        // null, 2));
                        // Payment ssarPayment14 = paymentRepository.save(newPayment(aa, lesson33,
                        // vBank, null, 3));
                        // Payment ssarPayment15 = paymentRepository.save(newPayment(aa, lesson40,
                        // vBank, null, 1));
                        // Payment ssarPayment16 = paymentRepository.save(newPayment(hong, lesson10,
                        // card, coupon1, 2));
                        // Payment ssarPayment17 = paymentRepository.save(newPayment(hong, lesson19,
                        // kakaoPay, null, 1));
                        // Payment ssarPayment18 = paymentRepository.save(newPayment(hong, lesson36,
                        // card, null, 2));
                        // Payment ssarPayment19 = paymentRepository.save(newPayment(hong, lesson41,
                        // vBank, null, 3));
                        // Payment ssarPayment20 = paymentRepository.save(newPayment(hong, lesson48,
                        // vBank, null, 1));
                        // Payment ssarPayment21 = paymentRepository.save(newPayment(lala, lesson8,
                        // card, coupon1, 2));
                        // Payment ssarPayment22 = paymentRepository.save(newPayment(lala, lesson11,
                        // kakaoPay, null, 1));
                        // Payment ssarPayment23 = paymentRepository.save(newPayment(lala, lesson31,
                        // card, null, 2));
                        // Payment ssarPayment24 = paymentRepository.save(newPayment(lala, lesson44,
                        // vBank, null, 3));
                        // Payment ssarPayment25 = paymentRepository.save(newPayment(lala, lesson45,
                        // vBank, null, 1));

                        Interest ssarInterest1 = interestRepository.save(newInterest(ssar, beauty));
                        Interest ssarInterest2 = interestRepository.save(newInterest(ssar, sports));
                        Interest ssarInterest3 = interestRepository.save(newInterest(ssar, dance));
                        Interest ssarInterest4 = interestRepository.save(newInterest(cos, beauty));
                        Interest ssarInterest5 = interestRepository.save(newInterest(cos, sports));
                        Interest ssarInterest6 = interestRepository.save(newInterest(cos, art));
                        Interest ssarInterest7 = interestRepository.save(newInterest(cos, crafts));
                        Interest ssarInterest8 = interestRepository.save(newInterest(cos, game));
                        Interest ssarInterest9 = interestRepository.save(newInterest(aa, beauty));
                        Interest ssarInterest10 = interestRepository.save(newInterest(aa, sports));
                        Interest ssarInterest11 = interestRepository.save(newInterest(aa, dance));
                        Interest ssarInterest12 = interestRepository.save(newInterest(aa, music));
                        Interest ssarInterest13 = interestRepository.save(newInterest(aa, crafts));
                        Interest ssarInterest14 = interestRepository.save(newInterest(aa, game));
                        Interest ssarInterest15 = interestRepository.save(newInterest(hong, beauty));
                        Interest ssarInterest16 = interestRepository.save(newInterest(lala, sports));
                        Interest ssarInterest17 = interestRepository.save(newInterest(lala, art));
                        Interest ssarInterest18 = interestRepository.save(newInterest(lala, crafts));

                        Claim claim1 = claimRepository.save(newClaim(expert1));
                        Claim claim2 = claimRepository.save(newClaim(expert2));
                };

        }
}
