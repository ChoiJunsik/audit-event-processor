CREATE
USER 'junsik-local'@'localhost' IDENTIFIED BY 'junsik-local';
CREATE
USER 'junsik-local'@'%' IDENTIFIED BY 'junsik-local';

GRANT ALL PRIVILEGES ON *.* TO
'junsik-local'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO
'junsik-local'@'%';

CREATE
DATABASE chat DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE chat.message
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '메세지 PK',
    `receiver_id`              BIGINT       NOT NULL COMMENT '전송 대상 사용자 PK',
    `content`                  VARCHAR(255) NOT NULL COMMENT '메세지 내용',
    `create_user`           BIGINT       NOT NULL DEFAULT 0 COMMENT '생성자 (작업시작자)',
    `create_date`           DATETIME(6) NOT NULL COMMENT '생성시간',
    `update_user`           BIGINT       NOT NULL DEFAULT 0 COMMENT '변경자 (작업변경자)',
    `update_date`           DATETIME(6) NOT NULL COMMENT '변경시간',
    PRIMARY KEY (id)
);

ALTER TABLE chat.message COMMENT '채팅 메세지';


CREATE TABLE chat.users
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '유저 PK',
    `name`                  VARCHAR(50) NOT NULL COMMENT '유저 이름',
    PRIMARY KEY (id)
);

ALTER TABLE chat.users COMMENT '채팅 사용자';
