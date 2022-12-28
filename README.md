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

## :movie_camera: 1. 제작 기간 & 팀원 소개
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

## :bulb: 3. 사용자 시나리오

### 회원가입 및 로그인
![회원가입_hobbyup](https://user-images.githubusercontent.com/95184357/209790064-d0dd06fb-2684-471d-8bf7-54d7d98aa311.gif)
![로그인_hobbyup](https://user-images.githubusercontent.com/95184357/209790173-675d3fcd-bb4e-4264-ad4a-9b80995f1486.gif)

### 프로필 등록, 수정
![프로필 등록하기_hobbypup](https://user-images.githubusercontent.com/95184357/209791540-2f6a1a65-ec25-4c01-b1dd-96413fb79108.gif)
![프로필 수정하기_hobbyup](https://user-images.githubusercontent.com/95184357/209791659-5ad4cf41-9121-4171-8561-ccf98ca4ca71.gif)