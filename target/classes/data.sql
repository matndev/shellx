-- TRUNCATE roles CASCADE;
INSERT INTO roles (roles_id, roles_name) VALUES (0, 'ROLE_USER'), (1, 'ROLE_ADMIN'), (2, 'ROLE_MODERATOR');
INSERT INTO authorities (authorities_id, authorities_name, authorities_role) VALUES (1, 'READ_ACCESS', 1), (2, 'WRITE_ACCESS', 1), (3, 'READ_ONLY', 0);