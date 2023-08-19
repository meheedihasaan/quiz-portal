## Quiz Portal

### Description
This is a simple Quiz Portal site where you can evaluate your knowledge on different type of quizzes.

Live: https://quiz-portal.up.railway.app/

### Credentials
* Admin - Email: mahin@gmail.com, Password: mahin
* User  - Email: ahsan@gmail.com, Password: ahsan

### Technology
* Language: Java 17
* Build Tool: Maven
* Spring Boot: 3.0.0
* Database: PostgreSQL

### Feature
- [x] Users, Categories, Quizzes, Questions [CRUD]
- [x] Validation & Constraints for CRUD
- [x] Quiz History For Admin
- [x] Quiz History For Participant
- [x] Timer On Start Quiz
- [x] Evaluating Answer
- [x] Security: Role Based Authorization

### Clone Repository
Clone this repository from https://github.com/meheedihasaan/quiz-portal.git or use the below command for the GitHub CLI:

```
gh repo clone meheedihasaan/quiz-portal
```

### Build & Run
There are several ways to build and run a Spring Boot application. After cloning this repository, you can build it using the below command:

```
mvn clean install
```

Before giving this command, make sure that Maven is already installed on your PC. This command will clear the target folder and previously generated JAR files, build an artifact, and then create a new JAR file,  <span style = "color: cyan">quiz-portal-0.0.1-SNAPSHOT.jar</span> inside the target directory. Now, you can run this JAR file using the below command:

```
java -jar target\quiz-portal-0.0.1-SNAPSHOT.jar
```

Also, you can directly run this application without an explicit build using the below command:

```
mvn spring-boot:run
```

Or, you can run this application via the <span style = "color: cyan">Run</span> button of your preferred IDE.

### User Manuals
There are two types of user - Admin and Participant. Admin can create categories and manage them. Admin can create quizzes on different categories and manage them. Admin can add question into these quizzes. Admin can activated and deactivated a particular quiz. A participant have to register first and then he can participate in a quiz any number of times. He can evaluate his answer.