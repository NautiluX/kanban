#!/bin/bash

source config.sh

NEW_USERNAME=false
NEW_PASSWORD=false
ADMIN=false

while getopts "au:p:" arg; do
  case $arg in
    h)
      echo "usage" 
      ;;
    u)
      NEW_USERNAME=$OPTARG
      ;;
    p)
      NEW_PASSWORD=$OPTARG
      ;;
    a)
      ADMIN=true
      ;;
  esac
done

if [[ "${NEW_USERNAME}" == "false" || "${NEW_PASSWORD}" == "false" ]]; then
    echo "Username and password needed"
    exit 1;
fi

mysql -u ${YUKAN_DB_USER} --password=${YUKAN_DB_PASSWORD} << EOF
INSERT INTO kanban.USERS (USER_NAME, PASSWORD) VALUES ('${NEW_USERNAME}', '${NEW_PASSWORD}');
INSERT INTO kanban.USER_ROLES (USER_NAME, ROLE_NAME) VALUES ('${NEW_USERNAME}', 'kanban_user');
EOF

if [[ "${ADMIN}" == "true" ]]; then
mysql -u ${YUKAN_DB_USER} --password=${YUKAN_DB_PASSWORD} << EOF
INSERT INTO kanban.USER_ROLES (USER_NAME, ROLE_NAME) VALUES ('${NEW_USERNAME}', 'kanban_admin');
EOF
fi
