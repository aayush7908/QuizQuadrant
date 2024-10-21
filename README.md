# QuizQuadrant

## Introduction

QuizQuadrant is a web application designed to provide an online platform for practicing quiz questions. It has to two distinct types of users: Student and Teacher.


## Tech Stack

- Spring Boot
- PostgreSQL
- Next.js


## Installation
To clone the repository, open git bash in your desired directory and execute the below given command:

```
https://github.com/aayush7908/QuizQuadrant.git
```

After this, a new directory named `QuizQuadrant` will be created within the current working directory.
Create a `.env` file in that folder with following key-value pairs:

```
NEXT_PUBLIC_BACKEND_BASE_URL="http://localhost:3000/api"
DB_NAME="quizquadrant"
DB_URL="jdbc:postgresql://database/quizquadrant"
DB_USER="postgres"
DB_PASSWORD=""
DEVELOPER_EMAIL=""
SMTP_USERNAME=""
SMTP_PASSWORD=""
```

*__NOTE:__ Empty values have to be replaced by your own credentials.*

After `.env` file is ready, use the following docker command to run the application:

```
docker-compose up --build -d
```

*___INFO:___ Docker will create three containers, one for each of Client, Server and PostgreDQL Database. These containers will run in background. If you don't want them to run in background, then remove `-d` flag from docker command*

After this, your application is ready. You can use the application by pasting following code in your browser:

```
http://localhost:3000
```

To stop these containers, you can use the command:
```
docker-compose down
```



## Key Features

- Authentication using JWT.
- Forgot Password Service.
- Users can Practice publically available questions.
- Teacher can Create and publish new questions related to specific subjects and subtopics.
- Questions can be saved as draft.
- Admin panel for user management.


## Learning Outcomes

Developing QuizQuadrant provided with a practical exposure to:
- Building backend services with Spring Boot.
- Managing database with PostgreSQL.
- Creating dynamic and responsive UIs using Next.js.
- Implementing secure authentication and route protection.


## Contributors

- Aayush Dalal ([aayush7908](https://github.com/aayush7908))
- Dhruv Bhatt ([dhruvbhatt553](https://github.com/dhruvbhatt553))
