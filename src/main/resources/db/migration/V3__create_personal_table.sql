CREATE SEQUENCE personal_id_seq;
CREATE TABLE personal (
    id bigint DEFAULT NEXT VALUE FOR personal_id_seq PRIMARY KEY,
    first_name varchar(256) NOT NULL,
    last_name varchar(256) NOT NULL,
    middle_name varchar(256) NOT NULL
);