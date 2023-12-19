UPDATE "users_tbl"
SET email = CONCAT(email, '${email-suffix}')
WHERE email IS NOT NULL AND NOT email LIKE '%${email-suffix}';
