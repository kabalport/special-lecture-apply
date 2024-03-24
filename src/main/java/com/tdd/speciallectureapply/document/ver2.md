# DDL
```sql
CREATE TABLE IF NOT EXISTS special_lecture_info (
`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '내부 인조키',
`special_lecture_id` CHAR(36) NOT NULL COMMENT '특강 ID',
`special_lecture_date` DATE NOT NULL COMMENT '특강 날짜',
`max_capacity` INT NOT NULL DEFAULT 30 COMMENT '최대 정원',
`current_applications` INT NOT NULL DEFAULT 0 COMMENT '현재 신청 수',
PRIMARY KEY (`id`),
UNIQUE KEY `uuid_unique` (`special_lecture_id`),
UNIQUE KEY `unique_date` (`special_lecture_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='특강정보';

CREATE TABLE IF NOT EXISTS special_lecture_apply_info (
`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '내부 인조키',
`apply_id` CHAR(36) NOT NULL COMMENT '신청 ID',
`special_lecture_id` CHAR(36) NOT NULL COMMENT '특강 ID, UUID 사용',
`special_lecture_date` DATE NOT NULL COMMENT '특강 날짜',
`user_id` CHAR(36) NOT NULL COMMENT '사용자 ID, UUID 사용',
`special_lecture_apply_status` ENUM('pending', 'accepted', 'rejected') NOT NULL COMMENT '특강 신청 상태',
UNIQUE KEY `unique_user_per_lecture` (`special_lecture_date`, `user_id`),
PRIMARY KEY (`id`),
UNIQUE KEY `uuid_unique` (`apply_id`),
FOREIGN KEY (`special_lecture_id`) REFERENCES special_lecture_info(`special_lecture_id`)
ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='특강신청정보';
```