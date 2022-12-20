### 최종 프로젝트 - 온라인 취미 클래스 수강 사이트 hobbyup

### 테이블 생성 구문

```sql

CREATE TABLE users (
	id	bigint	NOT NULL,
	username	varchar	NOT NULL,
	password	varchar	NOT NULL,
	email	varchar	NOT NULL,
	phone_num	varchar	NOT NULL,
	created_at	TimeStamp	NOT NULL,
	updated_at	TimeStamp	NOT NULL,
	role	varchar	NOT NULL default 'USER'
);

CREATE TABLE profile (
	id	bigint	NOT NULL,
	photo	varchar	NOT NULL,
	introduction	longtext	NOT NULL,
	region	varchar	NOT NULL,
	certification	varchar	NOT NULL,
	career_year	varchar	NOT NULL,
	career	varchar	NOT NULL,
	user_profile_id	bigint	NOT NULL
);

CREATE TABLE subscribe (
	id	bigint	NOT NULL,
	created_at	TimeStamp	NOT NULL,
	user_id	bigint	NOT NULL,
	lecture_id	bigint	NOT NULL
);


CREATE TABLE lesson (
	id	bigint	NOT NULL,
	name	varchar	NOT NULL,
	photo	varchar	NOT NULL
	price	bigint	NOT NULL,
	curriculum	longtext	NOT NULL,
	lesson_time	int	NOT NULL,
	lesson_count	int	NOT NULL,
	possible_days	varchar	NOT NULL,
	place	varchar	NOT NULL,
	policy longtext	NOT NULL,
	expired_at	TimeStamp	NOT NULL,
	user_id	bigint	NOT NULL,
	category_id	bigint	NOT NULL
);

CREATE TABLE alarm (
	id	bigint	NOT NULL
);

CREATE TABLE coupon (
	id	bigint	NOT NULL,
	title	varchar	NOT NULL,
	price	bigint	NOT NULL,
	expired_date	TimeStamp	NOT NULL,
	created_at	TimeStamp	NOT NULL,
	user_coupon_id	bigint	NOT NULL
);

CREATE TABLE payment (
	id	bigint	NOT NULL,
	total_price	bigint	NOT NULL,
	total_count	int	NOT NULL,
	discount_price	bigint	NOT NULL,
	final_price	bigint	NOT NULL,
	created_at	TimeStamp	NOT NULL,
	user_id	bigint	NOT NULL,
	lecture_id	bigint	NOT NULL,
	payment_type_id	bigint	NOT NULL
);

CREATE TABLE admin (
	id	bigint	NOT NULL,
	password	varchar	NOT NULL,
	name	varchar	NOT NULL,
	created_at	TimeStamp	NOT NULL
);

CREATE TABLE review (
	id	bigint	NOT NULL,
	content	longtext	NOT NULL,
	grade	double	NOT NULL,
	lecture_id	bigint	NOT NULL,
	user_id	bigint	NOT NULL
);

CREATE TABLE payment_type (
	id	bigint	NOT NULL,
	type varchar	NOT NULL
);


CREATE TABLE category (
	id	bigint	NOT NULL,
	name	varchar	NOT NULL
);

CREATE TABLE interest (
	id	bigint	NOT NULL,
	user_id	bigint	NOT NULL,
	category_id	bigint	NOT NULL
);

ALTER TABLE user ADD CONSTRAINT PK_USER PRIMARY KEY (
	id
);

ALTER TABLE profile ADD CONSTRAINT PK_PROFILE PRIMARY KEY (
	id
);

ALTER TABLE subscribe ADD CONSTRAINT PK_SUBSCRIBE PRIMARY KEY (
	id
);

ALTER TABLE lesson ADD CONSTRAINT PK_LESSON PRIMARY KEY (
	id
);

ALTER TABLE alarm ADD CONSTRAINT PK_ALARM PRIMARY KEY (
	notice_id
);

ALTER TABLE coupon ADD CONSTRAINT PK_COUPON PRIMARY KEY (
	id
);

ALTER TABLE payment ADD CONSTRAINT PK_PAYMENT PRIMARY KEY (
	id
);

ALTER TABLE admin ADD CONSTRAINT PK_ADMIN PRIMARY KEY (
	id
);

ALTER TABLE review ADD CONSTRAINT PK_REVIEW PRIMARY KEY (
	id
);

ALTER TABLE payment_type ADD CONSTRAINT PK_PAYMENT_TYPE PRIMARY KEY (
	id
);


ALTER TABLE category ADD CONSTRAINT PK_CATEGORY PRIMARY KEY (
	id
);

ALTER TABLE interest ADD CONSTRAINT PK_INTEREST PRIMARY KEY (
	id
);




```
