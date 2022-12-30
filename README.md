# :dart: hobbyup
온라인 취미 클래스 플랫폼 hobbyup

"더 이상 자신의 취미를 혼자만의 취미로만 두지 마세요!"
주 52시간이 정착이 되어가는 사회에서 여가생활을 잘 보내는 것은 선택이 아닌 필수가 되었습니다.
남부럽지 않은 취미 하나 정도는 가지고 싶은 세상, 하지만 누구에게 배우지?
내가 10년동안 열심히 이러한 취미를 남몰래 해왔는데, 과연 쓸모가 있을까?
그런 고민들은 hobbyup을 만나는 순간, 모두 해결!
원하는 취미 클래스를 손쉽게 구매해서 배울 수 있고, 회원의 관심사를 파악해서 매칭까지 해드리고, 원하는 전문가와 1:1 상담까지!
</br></br>

## :triangular_flag_on_post: 프로젝트 개요
### 레퍼런스 사이트(크몽)
![image](https://user-images.githubusercontent.com/95184357/209761704-869e35aa-3d01-41f5-a18c-d26fb38bdb5f.png)

'크몽'의 여러가지 서비스들 중에서 다양한 카테고리에 대한 전문성을 상품화하여 거래하는 서비스를 참조하였다.

### 개발 이유
1. 삶에서 여가의 중요성이 점점 증가하고 있다.
2. 단순히 클라이언트가 서버에서 데이터를 받는 것이 아니라, 클라이언트가 직접 다른 클라이언트에게 서비스를 제공하는 형태의 사이트를 구현하고 싶었다.
3. UI적인 측면에서 여러 사이트들을 둘러본 결과 '크몽'이 가장 마음에 들었다.

### 프롤로그
![image](https://user-images.githubusercontent.com/95184357/209767132-57089374-cb4e-4d9b-8011-18257704d31b.png)

### 개발 리포지토리 주소
- front-end(https://github.com/jungchungsub/class_project_front)
- back-end(https://github.com/gitthathonor/class_final_back)
- admin(https://github.com/gitthathonor/class_final_back_admin)

### 테이블 구성
![image](https://user-images.githubusercontent.com/95184357/209767385-a37e2ea3-8898-4e1c-941c-4455ded5d9a5.png)
```sql
CREATE TABLE `users` (
	`id`	bigint	NOT NULL,
	`username`	varchar	NOT NULL,
	`password`	varchar	NOT NULL,
	`email`	varchar	NOT NULL,
	`phone_num`	varchar	NOT NULL,
	`created_at`	TimeStamp	NOT NULL,
	`updated_at`	TimeStamp	NOT NULL,
	`role`	varchar	NOT NULL	COMMENT 'default USER',
	`is_disabled`	boolean	NOT NULL	COMMENT 'default false'
);

CREATE TABLE `profile` (
	`id`	bigint	NOT NULL,
	`photo`	varchar	NOT NULL,
	`introduction`	longtext	NOT NULL,
	`region`	varchar	NOT NULL,
	`certification`	varchar	NOT NULL,
	`career_year`	varchar	NOT NULL,
	`career`	varchar	NOT NULL,
	`user_profile_id`	bigint	NOT NULL
);

CREATE TABLE `subscribe` (
	`id`	bigint	NOT NULL,
	`created_at`	TimeStamp	NOT NULL,
	`user_id`	bigint	NOT NULL,
	`lecture_id`	bigint	NOT NULL
);

CREATE TABLE `lesson` (
	`id`	bigint	NOT NULL,
	`name`	varchar	NOT NULL,
	`photo`	varchar	NOT NULL	COMMENT '파일은 하드나 클라우드에 저장,  DB에는 경로만 저장',
	`price`	bigint	NOT NULL,
	`curriculum`	longtext	NOT NULL,
	`lesson_time`	int	NOT NULL,
	`lesson_count`	int	NOT NULL,
	`possible_days`	varchar	NOT NULL	COMMENT ',로 파싱해서 response 해야한다.',
	`place`	varchar	NOT NULL	COMMENT 'PlaceEnum 만들어서 지정해줘야 됨',
	`policy`	longtext	NOT NULL,
	`expired_at`	TimeStamp	NOT NULL,
	`category_id`	bigint	NOT NULL,
	`expert_id`	bigint	NOT NULL
);

CREATE TABLE `coupon` (
	`id`	bigint	NOT NULL,
	`title`	varchar	NOT NULL,
	`price`	bigint	NOT NULL,
	`expired_date`	TimeStamp	NOT NULL,
	`created_at`	TimeStamp	NOT NULL,
	`user_coupon_id`	bigint	NOT NULL
);

CREATE TABLE `payment` (
	`id`	bigint	NOT NULL,
	`total_price`	bigint	NOT NULL,
	`total_count`	int	NOT NULL,
	`discount_price`	bigint	NOT NULL,
	`final_price`	bigint	NOT NULL,
	`created_at`	TimeStamp	NOT NULL,
	`user_id`	bigint	NOT NULL,
	`lecture_id`	bigint	NOT NULL,
	`payment_type_id`	bigint	NOT NULL
);

CREATE TABLE `review` (
	`id`	bigint	NOT NULL,
	`content`	longtext	NOT NULL,
	`grade`	double	NOT NULL,
	`lesson_id`	bigint	NOT NULL,
	`user_id`	bigint	NOT NULL
);

CREATE TABLE `payment_type` (
	`id`	bigint	NOT NULL,
	`type`	varchar	NOT NULL
);

CREATE TABLE `category` (
	`id`	bigint	NOT NULL,
	`name`	varchar	NOT NULL
);

CREATE TABLE `interest` (
	`id`	bigint	NOT NULL,
	`user_id`	bigint	NOT NULL,
	`category_id`	bigint	NOT NULL
);

CREATE TABLE `expert` (
	`id`	bigint	NOT NULL,
	`satisfication`	int	NOT NULL,
	`total_work`	int	NOT NULL,
	`is_approval`	boolean	NOT NULL	COMMENT 'true일 때, 서비스 판매 가능',
	`user_id`	bigint	NOT NULL
);

CREATE TABLE `claim` (
	`id`	bigint	NOT NULL,
	`expert_id`	bigint	NOT NULL
);

ALTER TABLE `users` ADD CONSTRAINT `PK_USERS` PRIMARY KEY (
	`id`
);

ALTER TABLE `profile` ADD CONSTRAINT `PK_PROFILE` PRIMARY KEY (
	`id`
);

ALTER TABLE `subscribe` ADD CONSTRAINT `PK_SUBSCRIBE` PRIMARY KEY (
	`id`
);

ALTER TABLE `lesson` ADD CONSTRAINT `PK_LESSON` PRIMARY KEY (
	`id`
);

ALTER TABLE `coupon` ADD CONSTRAINT `PK_COUPON` PRIMARY KEY (
	`id`
);

ALTER TABLE `payment` ADD CONSTRAINT `PK_PAYMENT` PRIMARY KEY (
	`id`
);

ALTER TABLE `review` ADD CONSTRAINT `PK_REVIEW` PRIMARY KEY (
	`id`
);

ALTER TABLE `payment_type` ADD CONSTRAINT `PK_PAYMENT_TYPE` PRIMARY KEY (
	`id`
);

ALTER TABLE `category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
	`id`
);

ALTER TABLE `interest` ADD CONSTRAINT `PK_INTEREST` PRIMARY KEY (
	`id`
);

ALTER TABLE `expert` ADD CONSTRAINT `PK_EXPERT` PRIMARY KEY (
	`id`
);

ALTER TABLE `claim` ADD CONSTRAINT `PK_CLAIM` PRIMARY KEY (
	`id`
);

```
</br></br>

## :ferris_wheel: 1. 제작 기간 & 팀원 소개
* 2022년 11월 16일 ~ 2022년 12월 22일

| 이름 | 깃허브 링크 | 프론트&백엔드 |
| ----- | --- | --- |
| 이현성 | seong9566(https://github.com/seong9566) | 프론트 |
| 정충섭 | jungchungsub(https://github.com/jungchungsub) | 프론트 |
| 조현나 | hyonna12(https://github.com/hyonna12) | 백엔드 |
| 정수영 | gitthathonor(https://github.com/gitthathonor) | 백엔드 |
</br>

> **조원 역할 및 기능 개발 설명**
> 
> 
> > **이현성 Front**
> > 
> > - Figma를 이용해서 화면 설계 및 사용자 시나리오 작성
> > - 회원가입, 로그인, 프로필 페이지 작업 by flutter
> > - 각 페이지 RiverPod 라이브러리로 상태관리 작업
> > - 아임포트 API를 통한 결제 API 구현
> 
> > **정충섭 Front**
> > 
> > - 메인, 검색, 카테고리, 레슨, 결제 페이지 작업 by flutter
> > - 각 페이지 RiverPod 라이브러리로 상태관리 작업
> > - 더미데이터 작성
> 
> > **조현나 Back**
> > 
> > - 프로필 CRUD, 찜하기 기능, 마이페이지 기능 작업
> > - 관리자 페이지 작업
> > - 카카오 OAuth2.0 로그인 적용
> 
> > **정수영 Back**
> > 
> > - 회원가입, 로그인, 레슨 CRUD, 검색, 카테고리 추천, 결제 기능 작업
> > - 백엔드 서버 세팅
> > - Spring Security, JWT 적용
> > - 테이블 구성
> > - AWS의 EBS를 이용해 RDS와 함께 배포

<br/>

## :wrench: 2. 개발 환경
- Tool
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)

- FrontEnd
![Dart](https://img.shields.io/badge/dart-%230175C2.svg?style=for-the-badge&logo=dart&logoColor=white)
![Flutter](https://img.shields.io/badge/Flutter-%2302569B.svg?style=for-the-badge&logo=Flutter&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=Firebase&logoColor=white)

- BackEnd
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

- Database
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![h20](https://img.shields.io/badge/-h2-lightgrey)

- Team
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Markdown](https://img.shields.io/badge/markdown-%23000000.svg?style=for-the-badge&logo=markdown&logoColor=white)
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white)
</br></br>

## :bulb: 3. 사용자 시나리오

### 회원가입 및 로그인
![image](https://user-images.githubusercontent.com/95184357/209919272-aa826fbc-fe69-4b3e-bccc-158298b08f2f.png)

### 구매한 레슨 확인 및 리뷰작성
![image](https://user-images.githubusercontent.com/95184357/209919235-eee7cfd8-a333-40da-bc73-51128d162909.png)

### 프로필 등록/수정/삭제
![image](https://user-images.githubusercontent.com/95184357/209919301-ddc3d1ea-094f-41ee-b533-9cc9e357c377.png)

### 레슨 상세보기 및 결제하기
![image](https://user-images.githubusercontent.com/95184357/209919333-6c1a7fd7-2c7b-4a36-b9b2-ba10b910bf0f.png)

### 결제/취소 내역 보기
![image](https://user-images.githubusercontent.com/95184357/209919361-fe583760-4a3b-411a-a103-524ee695ba6d.png)

### 1:1 문의하기
![image](https://user-images.githubusercontent.com/95184357/209919384-51a39723-ccb7-4124-8e7b-288d957fef99.png)

### 찜한 레슨 목록보기
![image](https://user-images.githubusercontent.com/95184357/209919400-6eebfe7f-2c0f-4015-92cb-f84ffb31e0cf.png)

### 검색하기
![image](https://user-images.githubusercontent.com/95184357/209919415-9d3dcce8-edf9-4cca-9a36-52dddee9252c.png)

### 전문가 서비스 - 레슨 등록, 판매내역보기
![image](https://user-images.githubusercontent.com/95184357/209919436-c9940fc6-e59c-412b-89cd-db290d9d5062.png)

### 관리자 페이지
![image](https://user-images.githubusercontent.com/95184357/209921221-3997b158-7e05-4145-869d-f64050306565.png)
![image](https://user-images.githubusercontent.com/95184357/209921243-ecb44cf3-c043-4238-ab4d-aff93e352107.png)
</br></br>

## :video_camera: 4. 시연영상
hobbyup(https://www.youtube.com/watch?v=8IVljJ7U0zk0)
</br></br>

## :traffic_light: 5. 코드 컨벤션

### 프론트엔드
- 페이지 이동과 관련한 메서드는 move, 페이지 갱신과 관련된 메서드는 refresh를 붙임
- 라우팅 주소는 동사+명사

### 백엔드
- 카멜케이스를 원칙으로 해서 적용
- Controller와 Service의 메서드명을 일치
- 본 코드에서는 Logger사용, 테스트 코드에서는 System.out을 사용해서 디버깅
</br></br>

## :construction: 6. 아키텍쳐

### 프론트엔드 - MVVM
![image](https://user-images.githubusercontent.com/95184357/209915569-c9c7583b-7068-469b-81d1-d1d542dea292.png)
- view : 클라이언트에게 직접적으로 보여지는 레이어
- model : 데이터를 들고 있는 레이어
- viewModel : 데이터에 이벤트가 발생하면 model에 변화된 값을 적용시킴. view는 viewModel을 watch하고 있다. 
#### 장점
- view와 model이 독립적이므로 비즈니스 로직을 분리할 수 있다.
- 모듈화하여 개발 가능
- 여러개의 view가 하나의 viewModel을 의존할 수 있다.

### 백엔드 - MC
![image](https://user-images.githubusercontent.com/95184357/209915869-3d65d413-ac37-46ff-aed4-7f7108cd5c36.png)
- 스프링 기반의 개발에서 가장 보편적으로 사용되는 MVC 패턴에서 view만 빠진 형태
- flutter를 이용해서 프론트를 따로 개발하기 때문에 데이터 송수신을 위한 RESTful한 서버만이 필요해서 MC패턴이 되었다.
</br></br>

## :gem: 7. 주요 기술 및 로직

### Front 
#### 상태 관리를 위해서 Riverpod 라이브러리 적용
```yaml
dependencies:
  flutter:
    sdk: flutter
  flutter_rating_bar: ^4.0.1
  google_fonts: ^3.0.1
  font_awesome_flutter: ^10.2.1
  intl: ^0.17.0
  cached_network_image: ^3.2.1
  extended_image: ^6.0.1
  dropdown_button2: ^1.9.1
  fluttertoast: ^8.1.1
  cupertino_icons: ^1.0.2
  flutter_lints: ^2.0.0
  multi_select_flutter: ^4.1.3
  image_picker: ^0.8.6
  flutter_riverpod: ^2.0.0-dev.9
  http: ^0.13.5
  http_parser: ^4.0.1
  shared_preferences: ^2.0.15
  validators: ^3.0.0
  logger: ^1.1.0
  get: ^4.6.5
  flutter_secure_storage: ^7.0.0 #토큰 저장 및 자동 로그인 
  iamport_webview_flutter: 3.0.4
  url_launcher: ^6.0.4
  uni_links: ^0.5.1
  json_annotation: ^4.7.0
```

#### StateNotifierProvider를 통한 상태관리
- 카테고리별 페이지에서 상태관리를 하기 위한 코드
```dart
import 'package:finalproject_front/dto/response/respone_dto.dart';
import 'package:finalproject_front/main.dart';
import 'package:finalproject_front/pages/category/components/category_page_model.dart';
import 'package:finalproject_front/service/category_service.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:logger/logger.dart';

final categoryPageViewModel = StateNotifierProvider.family.autoDispose<CategoryPageViewModel, CategoryPageModel, int>((ref, cateogryId) {
  return CategoryPageViewModel(CategoryPageModel(null), cateogryId)..notifyViewModel("등록순");
});

class CategoryPageViewModel extends StateNotifier<CategoryPageModel> {
  final CategoryService categoryService = CategoryService();
  final mContext = navigatorKey.currentContext;
  final int categoryId;

  CategoryPageViewModel(super.state, this.categoryId);

  Future<void> notifyViewModel(String dropdownValue) async {
    String? jwtToken;

    ResponseDto responseDto = await categoryService.fetchCategoryList(categoryId, drowdownValue: dropdownValue);

    // if (responseDto.data == null) {
    //   state =
    // }
    Logger().d("상태메시지 : ${responseDto.msg}");
    Logger().d("상태데이터 : ${responseDto.data}");
    Logger().d("상태코드 : ${responseDto.statusCode}");

    if (responseDto.statusCode < 300) {
      state = CategoryPageModel(responseDto.data);
    }
  }
}
```

</br>

### Back

#### 아임포트 API를 이용한 결제API

- 라이브러리 DI & jitpack 
```groovy
repositories {
	mavenCentral()
	maven { url 'https://jitpack.io' } 
}

dependencies {
  ...
  ...
  
  
  // 아임포트 결제 API
  implementation 'com.github.iamport:iamport-rest-client-java:0.2.22'

}
```
- API 사용을 위해서 발급받은 key를 매개변수로 넣어서 아임포트 서버에 요청
```java
// flutter에서 결제페이지에 요청한 데이터가 아임포트 서버에 입력된 데이터와 일치 하는지 검증하는 컨트롤러
  @PostMapping("/verifyIamport/{imp_uid}")
  public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid)
      throws IamportResponseException, IOException {
    log.info("paymentByImpUid 진입");
    return iamportClient.paymentByImpUid(imp_uid);
  }
```
- 인증된 상태에서 결제기록을 저장하는 컨트롤러
```java
// 결제정보 입력
  @PostMapping("/api/lesson/{lessonId}/payment")
  public ResponseEntity<?> savePayment(@RequestBody PaymentSaveReqDto paymentSaveReqDto,
      @AuthenticationPrincipal LoginUser loginUser, @PathVariable Long lessonId) {
    PaymentSaveRespDto paymentSaveRespDto = paymentService.savePayment(paymentSaveReqDto, loginUser.getUser().getId(),
        lessonId);
    return new ResponseEntity<>(new ResponseDto<>("결제 완료", paymentSaveRespDto), HttpStatus.CREATED);
  }
```

#### 카카오 OAuth2.0 적용
- 라이브러리 DI
```groovy
dependencies {
  ...
  ...
  
  
  // OAuth 2.0
  implementation "org.springframework.boot:spring-boot-starter-oauth2-client"

}
```
- OAuth2.0 방식을 이용해서 외부 리소스 서버의 정보를 토큰을 요청해서 필요한 정보만 담아오는 컨트롤러
```java
@GetMapping("/oauth/kakao")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {

        // 토큰 요청
        OAuthToken oAuthToken = kakaoService.tokenRequest(code);
        // 받은 토큰으로 유저정보 요청
        KakaoProfile kakaoProfile = kakaoService.userInfoRequest(oAuthToken);

        String email = kakaoProfile.getKakao_account().getEmail();
        int index = email.indexOf("@");
        String username = (kakaoProfile.getKakao_account().getEmail().substring(0,
                index));

        KakaoRespDto kakaoUser = KakaoRespDto.builder()
                .username(username)
                .password(kakaoProfile.getKakao_account().getEmail() + kakaoProfile.getId())
                .email(email)
                .oauth("kakao")
                .build();

        return new ResponseEntity<>(new ResponseDto<>("카카오 유저정보", kakaoUser), HttpStatus.OK);
    }
```
- API 문서에 맞게 토큰DTO를 설정
```java
package site.hobbyup.class_final_back.oauth.dto;

import lombok.Data;

@Data
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
```

#### 추천순, 인기순, 등록순 매칭 
- QLRM 라이브러리를 이용해서 JPA기술에 DTO 리턴 적용
```java
public List<LessonCategoryListRespDto> findAllLessonCategoryListByUserId(Long userId, Long categoryId, String sort,
      Long minPrice,
      Long maxPrice) {
    log.debug("디버그 : LessonRepositoryQuery - findAllLessonCategoryListByUserId실행");
    String sql = "select l.id as lessonId, l.name as lessonName,";
    sql += " l.price as lessonPrice,";
    sql += " COUNT(r.id) AS totalReviews,";
    sql += " (case when AVG(r.grade) IS null then 0.0 ELSE AVG(r.grade) END) AS avgGrade,";
    sql += " (case when s.lesson_id IS NOT NULL then true ELSE false END) AS subscribed,";
    sql += " (case when i.category_id IS NOT NULL then true ELSE false END) AS recommand,";
    sql += " (case when s2.count IS null then 0 ELSE s2.count END) AS ranking,";
    sql += " l.created_at AS recent";
    sql += " FROM lesson l LEFT OUTER JOIN review r ON l.id = r.lesson_id";
    sql += " LEFT OUTER JOIN (SELECT lesson_id FROM subscribe WHERE user_id = :userId) s";
    sql += " ON l.id = s.lesson_id";
    sql += " LEFT OUTER JOIN (SELECT category_id FROM interest WHERE user_id = :userId) i";
    sql += " ON l.category_id = i.category_id";
    sql += " LEFT OUTER JOIN (SELECT COUNT(*) count, lesson_id FROM subscribe GROUP BY lesson_id) s2";
    sql += " ON l.id = s2.lesson_id";
    sql += " WHERE l.category_id = :categoryId";
    sql += " GROUP BY lessonId";

    if (minPrice != 0L || maxPrice != 0L) {
      sql += " HAVING :minPrice < lessonPrice AND lessonPrice < :maxPrice";
    }

    if (sort.equals("recommand")) {
      sql += " ORDER BY recommand DESC";
    } else if (sort.equals("ranking")) {
      sql += " ORDER BY ranking DESC";
    } else if (sort.equals("recent")) {
      sql += " ORDER BY recent DESC";
    }
    log.debug("디버그 : sql = " + sql);

    // 쿼리 완성
    JpaResultMapper jpaResultMapper = new JpaResultMapper();
    Query query = em.createNativeQuery(sql)
        .setParameter("userId", userId)
        .setParameter("categoryId", categoryId);
    if (minPrice != 0L || maxPrice != 0L) {
      query.setParameter("minPrice", minPrice)
          .setParameter("maxPrice", maxPrice);
    }

    log.debug("디버그 : query = " + query);

    List<LessonCategoryListRespDto> result = jpaResultMapper.list(query, LessonCategoryListRespDto.class);
    log.debug("디버그 : result = " + result);
    return result;
  }
```

#### Entity생성 시, AuditingTime 설정
- Entity에서 상속받게 할 AuditingTime 추상 클래스 생성
```java
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass // 이 클래스를 상속받은 Entity를 생성 시, 아래의 필드값을 추가해서 컬럼지정
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingTime {

    @LastModifiedDate // Insert, update시에 현재시간 들어감
    @Column(nullable = false)
    protected LocalDateTime updatedAt;

    @CreatedDate // Insert시에 현재시간 들어감
    @Column(nullable = false)
    protected LocalDateTime createdAt;

}
```
- 전체 프로그램 실행 시, 적용시키는 @EnableJpaAuditing
```java
package site.hobbyup.class_final_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClassFinalBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassFinalBackApplication.class, args);
	}

}
```

#### 더미데이터 설정을 위한 코드
```java
@RequiredArgsConstructor
@Configuration
public class DevInit extends DummyEntity {

        @org.springframework.context.annotation.Profile("dev")
        @Bean
        public CommandLineRunner dataSetting(UserRepository userRepository, ...) {
	...
	...
	...
	
	}
```

#### JWT로 인증 및 인가
- jwt를 변환해서 리턴해주는 프로세스 코드
```java
package site.hobbyup.class_final_back.config.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import site.hobbyup.class_final_back.config.auth.LoginUser;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.user.User;

public class JwtProcess {

    public static String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject(loginUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getRole().name())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return JwtProperties.TOKEN_PREFIX + jwtToken;
    }

    public static LoginUser verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).role(UserEnum.valueOf(role)).build();
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }
}
```
- jwt 인증 코드
```java
    // post의 /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        log.debug("디버그 : attemptAuthentication 요청됨");

        try {
            ObjectMapper om = new ObjectMapper();
            LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(),
                    loginReqDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        log.debug("디버그 : successfulAuthentication 요청됨");
        // 1. 세션에 있는 UserDetails 가져오기
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();

        // 2. 세션값으로 토큰 생성
        String jwtToken = JwtProcess.create(loginUser);

        // 3. 토큰을 헤더에 담기
        response.addHeader(JwtProperties.HEADER_KEY, jwtToken);

        // 4. 토큰 담아서 성공 응답하기
        LoginRespDto loginRespDto = new LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        log.debug("디버그 : unsuccessfulAuthentication 요청됨");
        CustomResponseUtil.fail(response, "로그인 실패");
    }
```
- jwt 인가 코드
```java
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 1. 헤더검증 후 헤더가 있다면 토큰 검증 후 임시 세션 생성
        if (isHeaderVerify(request, response)) {
            // 토큰 파싱하기 (Bearer 없애기)
            String token = request.getHeader(JwtProperties.HEADER_KEY)
                    .replace(JwtProperties.TOKEN_PREFIX, "");
            // 토큰 검증
            LoginUser loginUser = JwtProcess.verify(token);

            // 임시 세션 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser,
                    null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("디버그 : 토큰 검증 완료, 필터탐");
        }

        // 2. 세션이 있는 경우와 없는 경우로 나뉘어서 컨트롤러로 진입함
        log.debug("디버그 : 그냥 필터 탐");
        chain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(JwtProperties.HEADER_KEY);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
```

#### Base64를 활용한 이미지 변환
- 파일 decoding 코드
```java
	// base64 디코딩
        // byte[] stringToByte = encodeFile.getBytes(); // 문자열을 바이트로 변환
        byte[] decodeByte = Base64.decodeBase64(encodeFile);

        // 이미지 이름
        // String fileName = UUID.randomUUID().toString();
        String filePath = "C:\\Temp\\upload\\" + decodeByte + ".jpg";

        // 이미지 저장
        fos = new FileOutputStream(filePath); // 현위치에 path명으로 파일생성
        fos.write(decodeByte); // 파일에 buffer의 모든 내용 출력
        fos.close();
        return filePath;
```

## :gift: 8. 프로젝트 소감
> 3번의 프로젝트를 진행하고, 3번의 팀장을 맡아서 다양한 사람들과 협업을 할 수 있었던 기회가 생겨서 아주 좋았습니다. 특히, 최종 프로젝트는 6개월간 우리가 배우면서 다졌던 것들을 전부 쏟아내는 것이였기 때문에 더욱 가치가 있어습니다. 프로젝트를 진행하면서 실제 서비스는 우리가 보이는 것보다 훨씬 복잡하다는 것을 알게되었고 특히 프론트엔드와의 협업은 정말 세심한 대처가 필요하다는 점에 대해서 알게 되었습니다. 저를 끝까지 믿고 4주간 프로젝트를 위해서 고생한 저희 팀원들에게 무한한 감사를 드리고 앞으로 저는 배운 내용을 바탕으로 멋진 개발자가 되겠습니다. ***정수영***

> 이전 프로젝트와는 다르게 파이널 프로젝트에서는 다양한 기능들을 맡게 되면서 기능이 동작할 때, 어떤 과정으로 진행이 되는지 많이 생각해보면서 코드를 작성하였습니다. 이는 기능 구현에 많은 도움이 되었고 앞으로 개발자의 삶에 있어서도 중요한 경험이 되었습니다. 다만 짧은 기간내에 배웠던 개념들을 전부 다 공부하기 쉽지 않아서 해보고 싶던 기능들을 다 구현해보지 못한 점은 아쉽습니다. ***조현나***

> 6개월동안 수업을 들으면서 학교에서 이론으로만 배워서 아쉬웠던 부분을 실무적인 측면에서도 배우게 되고 팀원들과 협업을 하면서 3번의 프로젝트를 하게 되어서 너무 좋았습니다. 
>
> 1,2차 프로젝트에서는 백엔드를 맡아서 했지만 이번 최종에서는 프론트를 맡아서 하게 되었는데, java에 비해서 flutter는 생소한 기술이어서 적응에 시간이 오래걸렸습니다. 그래서 뭔가 완벽하게 MVVM패턴을 구현해내지 못한 것이 가장 아쉬움에 남았습니다. ***이현성***
>
> flutter로 프론트엔드 쪽을 맡아서 진행을 하였는데, 위젯을 사용하는 부분이 상당히 마음에 들었습니다. 남들에 비해서 저는 습득하고 활용하는 부분이 느려서 고민이 많았는데, 이번 프로젝트를 진행하면서 provider를 통해서 상태관리를 하는 부분에 있어서 어려움이 많아서 제가 맡았던 기능들을 완벽하게 만들진 못한 것 같아서 팀원들에게 미안했습니다. ***정충섭***
