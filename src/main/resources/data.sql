INSERT INTO roles(role_id, role_name)
VALUES (1, 'ROLE_SUPERADMIN'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_SALES'),
       (4, 'ROLE_WORKSHOP'),
       (5, 'ROLE_VALETER')
ON CONFLICT DO NOTHING;

INSERT INTO users(user_id, first_name, last_name, email, password, created_at, updated_at, is_enabled)
VALUES (1, 'Cleber', 'Balbinote', 'cleber@email.com', '$2a$12$eLMiEaNaqJQqsYyr3m8aM.cdltw.kIlPH1DryCAEztXadJuQts2Qi',
        now(), null, true)
ON CONFLICT DO NOTHING;

INSERT INTO users_roles(user_user_id, roles_role_id)
VALUES (1, 1)
ON CONFLICT DO NOTHING;

