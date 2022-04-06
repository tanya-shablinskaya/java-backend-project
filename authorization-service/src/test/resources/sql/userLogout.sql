INSERT INTO t_employee(f_id, f_login, f_password, f_email, f_name, f_create_date, f_status_id, f_failed_attempt,
                       f_locked_account)
VALUES (2, 'aaaaaaaaaa', '$2a$10$HLnG.IPbAHV/0nOkBPhZ5uqppjE.2rbb4UX6D6BNv4qMn8ME5BjLe', 'viextdfg@gmail.com', 'Оля',
        '2022-03-26 11:48:37.942545', 1, 0, 'false');

INSERT INTO t_session(f_id_session, f_username, f_date_of_start, f_date_of_finish, f_status_id)
VALUES ('eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYWFhYWFhYWFhIiwidXNlcklkIjoyLCJjaGVja1VzZXJJZCI6dHJ1ZSwiaWF0IjoxNjQ4NDAxNDI4LCJleHAiOjE3MTE1NDg5NzN9.gAdccA3HvRHwBexaSVpanRWj_5dgBN3ytMac06P4yPm9jXg1lnoHg-5WYzeeb17aYnnaUTw_lgXbpeHac7lA4g',
        'aaaaaaaaaa', '2022-03-27 04:19:14.960768', null, 1);