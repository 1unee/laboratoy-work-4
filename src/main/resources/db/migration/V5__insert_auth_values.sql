INSERT INTO personal (first_name, last_name, middle_name)
VALUES
    ('John', 'Doe', 'Michael'),         -- id is 1
    ('Jane', 'Smith', 'Elizabeth'),     -- id is 2
    ('Alice', 'Johnson', 'Marie'),      -- id is 3
    ('Robert', 'Brown', 'James'),       -- id is 4
    ('Emily', 'Davis', 'Anne'),         -- id is 5
    ('Michael', 'Wilson', 'David');     -- id is 6


-- password in decrypt kind is password123
INSERT INTO "user" (personal_id, username, password)
VALUES
    (1, 'johndoe', '$2a$10$ATp/E8CO7Gfpa.orY6GVfeiSM4ZqSkjD89aQykAVdzFftjyeOJdbC'),
    (2, 'janesmith', '$2a$10$ATp/E8CO7Gfpa.orY6GVfeiSM4ZqSkjD89aQykAVdzFftjyeOJdbC'),
    (3, 'alicejohnson', '$2a$10$ATp/E8CO7Gfpa.orY6GVfeiSM4ZqSkjD89aQykAVdzFftjyeOJdbC'),
    (4, 'robertbrown', '$2a$10$ATp/E8CO7Gfpa.orY6GVfeiSM4ZqSkjD89aQykAVdzFftjyeOJdbC'),
    (5, 'emilydavis', '$2a$10$ATp/E8CO7Gfpa.orY6GVfeiSM4ZqSkjD89aQykAVdzFftjyeOJdbC'),
    (6, 'michaelwilson', '$2a$10$ATp/E8CO7Gfpa.orY6GVfeiSM4ZqSkjD89aQykAVdzFftjyeOJdbC');


INSERT INTO role (name)
VALUES
    ('USER'),
    ('TEAM_LEAD'),
    ('DEVELOPER'),
    ('TESTER'),
    ('SYSTEM_ANALYST'),
    ('ADMIN');


INSERT INTO user_role_link (user_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6);
