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

### 깃허브 주소
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
![image](https://user-images.githubusercontent.com/95184357/209914141-509bb479-83a5-47a8-8de5-ff7cd1131a4b.png)

### 구매한 레슨 확인 및 리뷰작성
![image](https://user-images.githubusercontent.com/95184357/209914212-f62f296c-e577-4819-85f6-43a1129f72ff.png)

### 프로필 등록/수정/삭제
![image](https://user-images.githubusercontent.com/95184357/209914236-25f40010-51d6-4c41-ad54-27419693dcdf.png)

### 레슨 상세보기 및 결제하기
![image](https://user-images.githubusercontent.com/95184357/209914265-a89555d1-2509-4cb4-bc12-6fafcbd3e668.png)

### 결제/취소 내역 보기
![image](https://user-images.githubusercontent.com/95184357/209914286-74ee8126-95a2-4f24-adfe-ed301efd1063.png)

### 1:1 문의하기
![image](https://user-images.githubusercontent.com/95184357/209914305-882d74a3-13ac-4e9b-9210-14d4fa591b78.png)

### 찜한 레슨 목록보기
![image](https://user-images.githubusercontent.com/95184357/209914321-0299dd9b-f924-4698-abfb-c1311704cadd.png)

### 검색하기
![image](https://user-images.githubusercontent.com/95184357/209914334-ddd2f03f-7f32-4953-8754-ee0bdbe4f11d.png)

### 전문가 서비스 - 레슨 등록, 판매내역보기
![image](https://user-images.githubusercontent.com/95184357/209914373-5d8f91cf-f823-4b01-ba65-bb12392ce3d5.png)

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
```dart

```








