# Student Management REST API

This project is a REST API built to manage student data, including names, student numbers, and their corresponding grades by course code. If multiple entries exist for the same course, the API calculates and stores the average grade for that course.

## Table of Contents
- [Requirements](#requirements)
- [Technologies Used](#technologies-used)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
    - [Create Student](#create-student)
    - [Update Student](#update-student-grades)
- [Database](#database)
- [Error Handling](#error-handling)
- [Best Practices](#best-practices)

## Requirements
- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: Postgresql
- **Build Tool**: Maven
- **Java Version**: 17

## Technologies Used
- **Spring Boot**: For creating the REST API.
- **Spring Data JPA**: To interact with the database.
- **PostgreSQL**: An in-memory database for data persistence.
- **Lombok**: To reduce boilerplate code.
- **MapperUtil**: For object mapping between DTOs and entities.
- **Maven**: Build and dependency management.

## Setup

### Prerequisites
Make sure you have the following installed:
- Java 11+
- Maven 3.6+
- Git

### Steps to run the project
1. Clone the repository:
   ```bash
   git clone https://github.com/clkebr/zyfera.git
   ```

2. Navigate to the project directory:
   ```bash
   cd zyfera
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will be accessible at `http://localhost:8080`.

## API Endpoints

### 1. Create Student
- **URL**: `/api/students/create`
- **Method**: `POST`
- **Description**: Adds a new student along with their grades. If a course is repeated, the average grade for that course is stored.
- **Request Body**:
   ```json
   {
     "name": "Ali",
     "surname": "Yilmaz",
     "stdNumber": "B012X00012",
     "grades": [
       { "code": "MT101", "value": 90 },
       { "code": "PH101", "value": 75 },
       { "code": "CH101", "value": 60 },
       { "code": "MT101", "value": 70 },
       { "code": "HS101", "value": 65 }
     ]
   }
   ```
  - **Response**:
      - `201 Created` on success.
      - Example:
        ```json
        {
          "success": true,
          "message": "student is successfully created",
          "code": 201,
          "data": {
             "name": "Ali",
             "surname": "Yilmaz",
             "stdNumber": "B012X00012",
             "grades": [
                {
                  "code": "PH101",
                  "value": 75
                },
                {
                   "code": "MT101",
                   "value": 80
                },
                {
                   "code": "HS101",
                   "value": 65
                },
                {
                    "code": "CH101",
                    "value": 60
                }
            ],
            "id": 1
        }
       }
        ```

### 2. Update Student Grades
- **URL**: `/api/students/{stdNumber}`
- **Method**: `PUT`
- **Description**: Updates the grades of an existing student by averaging the new and old grades for courses.
- **Request Body**: Same as the `POST` request apart from student number.
- **Response**:
    - `200 OK` on success.

### Database
- The schema is automatically generated based on the JPA entities.
- You can access the console at `http://localhost:5432/zyfera` (username and  password that you are give in **application.properties**.

### Error Handling
- **400 Bad Request**: For invalid input data.
- **404 Not Found**: When trying to update a student that doesn't exist.
- **500 Internal Server Error**: For server-side issues.

### Best Practices Followed
- **SOLID Principles**: The application is designed with clean separation of concerns.
- **DTO Usage**: Data Transfer Objects (DTOs) are used for communication between layers.
- **Error Handling**: Centralized exception handling for clear and consistent error messages.
- **Documentation**: API documentation is available through this `README.md` file.


This `README.md` covers all the basic aspects of the project, including setup, API details, and best practices. Let me know if you need any further refinements!