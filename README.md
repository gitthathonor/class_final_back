# :dart: hobbyup
온라인 취미 클래스 플랫폼 hobbyup
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
![image](https://user-images.githubusercontent.com/95184357/209914395-e5b86f85-4c3d-41a7-a213-411e974af575.png)
![image](https://user-images.githubusercontent.com/95184357/209914413-dec9a9b0-9c86-4c9c-b523-8d6c43da4fd4.png)
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
- 상태 관리를 위해서 Riverpod 라이브러리 적용
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
</br>

### Back
</br>
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









