#!/bin/bash

set -x
docker run --rm -e MYSQL_ROOT_PASSWORD=toor -t -i -v $(pwd):/kanban mysql /kanban/db_test_inside.sh
