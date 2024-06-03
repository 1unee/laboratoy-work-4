DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    personal_id bigint REFERENCES personal(id),
    username varchar(255) NOT NULL UNIQUE,
    password varchar(1024) NOT NULL
);


CREATE TABLE `role` (
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(256) NOT NULL
);


-- CREATE TABLE user_role_link (
--     user_id BIGINT,
--     role_id BIGINT,
--     PRIMARY KEY (user_id, role_id),
--     FOREIGN KEY (user_id) REFERENCES `user`(id),
--     FOREIGN KEY (role_id) REFERENCES `role`(id)
-- );