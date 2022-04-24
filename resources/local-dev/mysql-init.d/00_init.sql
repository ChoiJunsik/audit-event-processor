CREATE
    USER 'junsik-local'@'localhost' IDENTIFIED BY 'junsik-local';
CREATE
    USER 'junsik-local'@'%' IDENTIFIED BY 'junsik-local';

GRANT ALL PRIVILEGES ON *.* TO
    'junsik-local'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO
    'junsik-local'@'%';

CREATE
    DATABASE event_store DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

#
# private AuditEventType eventType;
# private EventPublishType publishType;
#
# private String domain;
# private Long domainId;
# private Object payload;


CREATE TABLE event_store.event_store
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '이벤트 스토어 PK',
    `service`     VARCHAR(255) NOT NULL COMMENT '이벤트 발행 서비스',
    `event_time` DATETIME(6)  NOT NULL COMMENT '이벤트 발행 시간',
    `event_user` BIGINT       NOT NULL DEFAULT 0 COMMENT '이벤트 발행자',
    `event_type`     VARCHAR(45) NOT NULL COMMENT '이벤트 오퍼레이션 종류 | CREATED, UPDATED, DELETED',
    `publish_type`     VARCHAR(45) NOT NULL COMMENT '이벤트 구독 타입 | 즉시 컨슘, 예약 컨슘, 반복 컨슘',
    `domain`     VARCHAR(255) NOT NULL COMMENT '이벤트 도메인',
    `domain_id` BIGINT       NOT NULL COMMENT '이벤트 도메인 PK',
    `payload` JSON       NOT NULL COMMENT '이벤트 내용',
    PRIMARY KEY (id)
);

ALTER TABLE event_store.event_store
    COMMENT '이벤트 스토어';

CREATE TABLE event_store.message
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '메세지 PK',
    `receiver_id` BIGINT       NOT NULL COMMENT '전송 대상 사용자 PK',
    `content`     VARCHAR(255) NOT NULL COMMENT '메세지 내용',
    `create_user` BIGINT       NOT NULL DEFAULT 0 COMMENT '생성자 (작업시작자)',
    `create_date` DATETIME(6)  NOT NULL COMMENT '생성시간',
    `update_user` BIGINT       NOT NULL DEFAULT 0 COMMENT '변경자 (작업변경자)',
    `update_date` DATETIME(6)  NOT NULL COMMENT '변경시간',
    PRIMARY KEY (id)
);

ALTER TABLE event_store.message
    COMMENT '채팅 메세지';


CREATE TABLE event_store.users
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT COMMENT '유저 PK',
    `name` VARCHAR(50) NOT NULL COMMENT '유저 이름',
    PRIMARY KEY (id)
);

ALTER TABLE event_store.users
    COMMENT '채팅 사용자';
