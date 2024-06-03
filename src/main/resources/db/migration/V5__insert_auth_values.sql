-- INSERT INTO personal (id, first_name, last_name, middle_name)
-- VALUES
--     (1, 'John', 'Doe', 'Michael'),
--     (2, 'Jane', 'Smith', 'Elizabeth'),
--     (3, 'Alice', 'Johnson', 'Marie'),
--     (4, 'Robert', 'Brown', 'James'),
--     (5, 'Emily', 'Davis', 'Anne'),
--     (6, 'Michael', 'Wilson', 'David'),
--     (7, 'Olivia', 'Taylor', 'Grace');
--
--
-- INSERT INTO `user` (id, personal_id, username, password)
-- VALUES
--     (1, 1, 'johndoe', 'password123'),
--     (2, 2, 'janesmith', 'password123'),
--     (3, 3, 'alicejohnson', 'password123'),
--     (4, 4, 'robertbrown', 'password123'),
--     (5, 5, 'emilydavis', 'password123'),
--     (6, 6, 'michaelwilson', 'password123'),
--     (7, 7, 'oliviataylor', 'password123');
--
--
INSERT INTO `role` (id, `name`)
VALUES
    (1, 'ROLE_GUEST'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_ADMIN'),
    (4, 'ROLE_DEVELOPER'),
    (5, 'ROLE_TESTER'),
    (6, 'ROLE_SYSTEM_ANALYST'),
    (7, 'ROLE_TEAM_LEAD');
--
--
-- INSERT INTO user_role_link (user_id, role_id)
-- VALUES
--     (1, 1),
--     (2, 2),
--     (3, 3),
--     (4, 4),
--     (5, 5),
--     (6, 6),
--     (7, 7);
