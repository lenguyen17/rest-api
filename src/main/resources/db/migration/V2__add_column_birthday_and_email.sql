ALTER TABLE "users_tbl"
ADD COLUMN "birthday" DATE,
ADD COLUMN "email" VARCHAR(255);

INSERT INTO users_tbl (id, username, password, first_name, last_name, address, birthday, email)
VALUES (100, 'asd', 'asd', 'asd', 'asd', 'asd', '2000-10-10', 'EMAIL@GMAIL.COM');
