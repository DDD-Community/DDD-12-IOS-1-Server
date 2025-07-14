ALTER TABLE cafe_beverages
    ADD COLUMN sugar_level VARCHAR(20) NOT NULL
        DEFAULT 'UNKNOWN';
-- NULL 허용 불가 시 기본값 지정 (필요에 따라 조정)

-- 2) member_beverage_likes 테이블 생성
CREATE TABLE member_beverage_likes
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id    BIGINT      NOT NULL,
    beverage_id  BIGINT      NOT NULL,
    created_date DATETIME(6) NOT NULL,
    update_date  DATETIME(6) NOT NULL,
    -- member → members(member_id) 참조
    CONSTRAINT fk_mbl_member
        FOREIGN KEY (member_id)
            REFERENCES members (member_id)
            ON DELETE CASCADE,
    -- beverage → cafe_beverages(cafe_beverage_id) 참조
    CONSTRAINT fk_mbl_beverage
        FOREIGN KEY (beverage_id)
            REFERENCES cafe_beverages (cafe_beverage_id)
            ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
