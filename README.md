# School Management System

This project is a School Management System built with Java and Spring Boot. It provides a backend for managing schools, students, lecturers, courses, and addresses.

## Technologies Used

- **Java** – Main programming language
- **Spring Boot** – Application framework and auto-configuration
- **Spring Web (Spring MVC)** – RESTful web services and controllers
- **Spring Data JPA** – Data access and ORM layer
- **Hibernate** – JPA implementation used by Spring Data JPA
- **Maven** – Build and dependency management
- **H2 / SQL Database** – Relational database (in-memory or external, depending on configuration)

## Project Structure

- `src/main/java` – Application source code
  - `org.sms.schoolmanagementsystem` – Main application package
  - `controller` – REST controllers
  - `services` – Business logic layer
  - `repository` – JPA repositories
  - `entity` – JPA entities (School, Student, Lecturer, Course, Address, etc.)
- `src/main/resources`
  - `application.properties` – Application configuration
- `src/test/java` – Unit  tests

## API Documentation (Quick Look)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/api/v1/student/list` | Get all students |
| POST | `/api/v1/student/save` | Register a new student |
| PUT | `/api/v1/lecturer/update/{id}` | Update lecturer information |
| DELETE | `/api/v1/lecturer/dismiss/{id}` | Dismiss (delete) a lecturer |

### Prerequisites

- Java (version as defined in `pom.xml`, e.g. 17)
- Maven 3.8+ 

### Build and Run

From the project root directory:

```bash
mvn clean install
mvn spring-boot:run
```

On Windows using the Maven wrapper:

```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

After the application starts, it is available at:

- `http://localhost:8080`

## Running Tests

```bash
mvn test
```

## Useful References

- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#data.sql.jpa-and-spring-data)
- [Spring Web](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web)
- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
