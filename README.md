# Project YuKan
Find gold in your daily work!
The goal of this project is to provide a kanban board that can be used for both personal and business work. We want to enable the user to show tasks from differnt origins (aka. backends) which may be located in a protected network due to potential confidentiality of the data in the tasks themselves. Other tasks with less confidentiallity the user may want to store in a location where they can be accessed anytime. The system is implemented as a web service which can be accessed from mobile devices as well as from the users personal computer.

## Setup
- In root directory: docker-compose up -d
- In *java_backend* directory: mvn tomcat7:deploy
- go to *localhost:3333/backend/setup*
- go to *localhost:3333*

## Features
- Create new cards
- Drag cards from one lane to another
- Delete cards
- (planned) World-readable boards (share-by-link)
- (planned) Label cards using #label in content
- (planned) Filter by label
- (planned) Comment cards
- (planned) Create boards
- (planned) Connect to boards from different backends (private, cloud, corporate)
- (planned) Share boards
- (planned) Assign cards to different users
- (planned) Smart boards for labels
- (planned) Registration self service
