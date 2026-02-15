-- Category 초기 데이터 (MySQL)

-- 대분류 (Parent Categories)
INSERT IGNORE INTO category (id, parent_id, name, description, icon_url, category_type, created_at, updated_at) VALUES
(1, NULL, '운동', '운동 관련 카테고리', NULL, 'PARENT', NOW(), NOW()),
(2, NULL, '게임', '게임 관련 카테고리', NULL, 'PARENT', NOW(), NOW()),
(3, NULL, '언어', '언어 학습 관련 카테고리', NULL, 'PARENT', NOW(), NOW()),
(4, NULL, '음악', '음악 관련 카테고리', NULL, 'PARENT', NOW(), NOW()),
(5, NULL, '여행', '여행 관련 카테고리', NULL, 'PARENT', NOW(), NOW());

-- 소분류 - 운동
INSERT IGNORE INTO category (id, parent_id, name, description, icon_url, category_type, created_at, updated_at) VALUES
(101, 1, '헬스', '헬스 및 웨이트 트레이닝', NULL, 'CHILD', NOW(), NOW()),
(102, 1, '러닝', '러닝 및 마라톤', NULL, 'CHILD', NOW(), NOW()),
(103, 1, '축구', '축구', NULL, 'CHILD', NOW(), NOW()),
(104, 1, '테니스', '테니스', NULL, 'CHILD', NOW(), NOW());

-- 소분류 - 게임
INSERT IGNORE INTO category (id, parent_id, name, description, icon_url, category_type, created_at, updated_at) VALUES
(201, 2, 'PC게임', 'PC 게임', NULL, 'CHILD', NOW(), NOW()),
(202, 2, '모바일게임', '모바일 게임', NULL, 'CHILD', NOW(), NOW()),
(203, 2, '콘솔게임', '콘솔 게임', NULL, 'CHILD', NOW(), NOW());

-- 소분류 - 언어
INSERT IGNORE INTO category (id, parent_id, name, description, icon_url, category_type, created_at, updated_at) VALUES
(301, 3, '영어', '영어 학습', NULL, 'CHILD', NOW(), NOW()),
(302, 3, '일본어', '일본어 학습', NULL, 'CHILD', NOW(), NOW()),
(303, 3, '중국어', '중국어 학습', NULL, 'CHILD', NOW(), NOW());

-- 소분류 - 음악
INSERT IGNORE INTO category (id, parent_id, name, description, icon_url, category_type, created_at, updated_at) VALUES
(401, 4, '악기', '악기 연주', NULL, 'CHILD', NOW(), NOW()),
(402, 4, '보컬', '보컬 및 노래', NULL, 'CHILD', NOW(), NOW()),
(403, 4, '작곡', '작곡 및 편곡', NULL, 'CHILD', NOW(), NOW());

-- 소분류 - 여행
INSERT IGNORE INTO category (id, parent_id, name, description, icon_url, category_type, created_at, updated_at) VALUES
(501, 5, '국내여행', '국내 여행', NULL, 'CHILD', NOW(), NOW()),
(502, 5, '해외여행', '해외 여행', NULL, 'CHILD', NOW(), NOW()),
(503, 5, '캠핑', '캠핑 및 아웃도어', NULL, 'CHILD', NOW(), NOW());
