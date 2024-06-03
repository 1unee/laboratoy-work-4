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
    (1, 'USER'),
    (2, 'TEAM_LEAD'),
    (3, 'DEVELOPER'),
    (4, 'TESTER'),
    (5, 'SYSTEM_ANALYST'),
    (6, 'ADMIN');
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
