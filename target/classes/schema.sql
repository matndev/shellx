DROP TABLE IF EXISTS messages, users, roles, authorities CASCADE;

CREATE TABLE messages (
	messages_id			BIGSERIAL PRIMARY KEY,
	messages_author		VARCHAR (50),
	messages_receiver	VARCHAR (50),
	messages_content	TEXT,
	messages_visible	BOOLEAN,
	messages_date		TIMESTAMP
);

CREATE TABLE users (
	users_id			SERIAL PRIMARY KEY,
	users_username		VARCHAR (50) UNIQUE,
	users_email			VARCHAR (100) UNIQUE,
	users_password		VARCHAR(100),
	users_enabled		BOOLEAN,
	users_avatar		TEXT,
	users_date			TIMESTAMP,
	users_role			INT
);

CREATE TABLE roles (
	roles_id			SERIAL PRIMARY KEY,
	roles_name			VARCHAR (100) NOT NULL UNIQUE
);

CREATE TABLE authorities (
	authorities_id			SERIAL PRIMARY KEY,
	authorities_name		VARCHAR (50),
	authorities_role		INT
);

ALTER TABLE authorities
ADD CONSTRAINT fk_roles_authorities FOREIGN KEY (authorities_role) REFERENCES roles(roles_id);

ALTER TABLE users
ADD CONSTRAINT fk_roles_users FOREIGN KEY (users_role) REFERENCES roles(roles_id);
