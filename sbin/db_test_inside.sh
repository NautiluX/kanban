#!/bin/bash

set -x

/etc/init.d/mysql start
cd /kanban && ./setup.sh
mysql -u kanban --password=k4nban << EOF
SELECT * FROM kanban.USERS;
SELECT * FROM kanban.USER_ROLES;
EOF

cd /kanban && ./create_user.sh -u test_user -p test_pwd_user
cd /kanban && ./create_user.sh -a -u test_admin -p test_pwd_admin

mysql -u kanban --password=k4nban << EOF
SELECT * FROM kanban.USERS;
SELECT * FROM kanban.USER_ROLES;
EOF
