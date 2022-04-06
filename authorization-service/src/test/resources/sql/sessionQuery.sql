INSERT INTO t_employee(f_id, f_login, f_password, f_email, f_name, f_create_date, f_status_id, f_failed_attempt,
                       f_locked_account)
VALUES (6, 'Saaaaaaaaaa', '$2a$10$HLnG.IPbAHV/0nOkBPhZ5uqppjE.2rbb4UX6D6BNv4qMn8ME5BjLe', 'viexddfg@gmail.com', 'Оля',
        '2022-03-26 11:48:37.942545', 1, 0, 'false');

INSERT INTO t_session(f_id_session, f_username, f_date_of_start, f_date_of_finish, f_status_id)
VALUES ('eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTYWFhYWFhYWFhYSIsInVzZXJJZCI6NiwiY2hlY2tVc2VySWQiOnRydWUsImlhdCI6MTY0ODM4NTk1MiwiZXhwIjoxNjk4OTc1MTkyfQ.bMK1yNXx-OXHbwW2zzURLlwjUf-l0Wya7Kd4DrSsN-DC97VgvhQdP0YO2BtcgeZ-votX4cwzar7--xp4qd_tYg',
        'Saaaaaaaaaa', '2022-03-27 04:19:14.960768', null, 1);