DELETE FROM user_roles
WHERE user_id = 1;

DELETE FROM users
WHERE username = 'dkwon';

UPDATE user_roles
SET role_id = 2
WHERE user_id = 2;