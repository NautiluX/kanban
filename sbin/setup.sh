#!/bin/bash

source config.sh

mysql -u ${YUKAN_DB_ROOT} --password=${YUKAN_DB_ROOT_PASSWORD} << EOF
CREATE DATABASE kanban;
CREATE USER ${YUKAN_DB_USER} IDENTIFIED BY '${YUKAN_DB_PASSWORD}';
GRANT ALL PRIVILEGES ON kanban.* TO '${YUKAN_DB_USER}';

USE kanban;

CREATE TABLE USERS (USER_ID INTEGER NOT NULL AUTO_INCREMENT, USER_NAME NVARCHAR(200), PASSWORD NVARCHAR(200),  PRIMARY KEY(USER_ID));
CREATE TABLE USER_ROLES (USER_NAME NVARCHAR(200), ROLE_NAME NVARCHAR(200), PRIMARY KEY (USER_NAME, ROLE_NAME));
    

INSERT INTO USERS (USER_NAME, PASSWORD) VALUES ('${YUKAN_ADMIN_USER}', '${YUKAN_ADMIN_PASSWORD}');
INSERT INTO USER_ROLES (USER_NAME, ROLE_NAME) VALUES ('${YUKAN_ADMIN_USER}', 'kanban_user');
INSERT INTO USER_ROLES (USER_NAME, ROLE_NAME) VALUES ('${YUKAN_ADMIN_USER}', 'kanban_admin');
EOF
