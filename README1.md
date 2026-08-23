# SmartCare Hospital Management System

SmartCare Hospital Management System is a Java-based backend application developed for the **CCS1303 Object-Oriented Programming Coursework**.

The system is designed to manage the main operations of a hospital using **Java, Spring Boot, MySQL, Spring Data JPA, Hibernate and REST APIs**.

## Project Objectives

The main objectives of this project are to:

* Apply Object-Oriented Programming principles in a real-world application
* Develop a structured enterprise application using Spring Boot
* Connect a Java application with a MySQL relational database
* Develop RESTful APIs for hospital management operations
* Apply validation and exception handling
* Follow a layered software architecture
* Demonstrate clean, reusable and maintainable Java code

## Technologies

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Postman
* Git
* GitHub

## System Modules

The SmartCare Hospital Management System will contain the following modules:

1. Patient Management
2. Doctor Management
3. Department Management
4. Appointment Management
5. Admission Management
6. Room Management
7. Treatment Management
8. Laboratory Management
9. Billing and Payment Management

## Main Functionalities

### Patient Management

* Register patients
* View patient information
* Update patient details
* Delete patient records
* Search patients

### Doctor Management

* Add doctors
* View doctor information
* Update doctor details
* Delete doctors
* Search doctors
* Assign doctors to departments

### Department Management

* Add departments
* View department information
* Update departments
* Delete departments
* Assign doctors to departments

### Appointment Management

* Book appointments
* View appointments
* Update appointments
* Cancel appointments
* View doctor schedules
* Prevent appointment clashes for the same doctor at the same date and time

### Admission and Room Management

* Admit patients
* Allocate rooms
* View admissions
* Discharge patients
* Maintain room availability

Supported room categories include:

* General Ward
* Private Room
* ICU

### Treatment Management

* Record diagnoses
* Add prescriptions
* Add treatment notes
* Maintain patient medical history

### Laboratory Management

* Add laboratory tests
* Update laboratory test results
* View laboratory test information
* View patient laboratory history

### Billing and Payment Management

Bills will be managed using:

* Consultation charges
* Room charges
* Laboratory charges
* Medicine charges

The system will also maintain payment status and payment method information.

## Object-Oriented Programming Concepts

The project will demonstrate the following OOP concepts:

### Encapsulation

Private attributes with appropriate getter and setter methods will be used.

### Inheritance

Inheritance will be used where appropriate to create reusable and structured classes.

### Abstraction

Abstract classes and interfaces will be used to define common behaviours.

### Polymorphism

Method overriding and interface implementation will be used to demonstrate polymorphic behaviour.

### Association and Composition

Relationships will be implemented between entities such as:

* Patient and Appointment
* Patient and Treatment
* Patient and Bill
* Doctor and Department
* Admission and Room

## System Architecture

The application follows a layered architecture:

```text
Client / Postman
       |
       v
Controller Layer
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
Spring Data JPA / Hibernate
       |
       v
MySQL Database
```

### Entity Layer

Represents the database tables as Java classes.

### Repository Layer

Handles communication between the Spring Boot application and the database.

### Service Layer

Contains the main business logic of the system.

### Controller Layer

Handles REST API requests and responses.

## Planned Database Tables

The database is planned to contain the following main tables:

```text
departments
doctors
patients
appointments
rooms
admissions
treatments
laboratory_tests
bills
```

## Validation and Business Rules

The system will implement validations and business rules including:

* Patient and doctor names cannot be empty
* Contact numbers must be valid
* Doctor consultation fees must be greater than zero
* Appointment dates cannot be in the past
* A doctor cannot have two appointments at the same date and time
* An unavailable room cannot be allocated to another patient
* Bill amounts cannot be negative

## Exception Handling

Custom exceptions and error-handling mechanisms will be implemented for situations such as:

* Patient not found
* Doctor not found
* Appointment not found
* Appointment conflicts
* Room unavailable
* Invalid input data

## REST API Testing

REST APIs will be developed using Spring Boot and tested using **Postman**.

CRUD operations will be implemented where applicable:

```text
POST    - Create
GET     - Read
PUT     - Update
DELETE  - Delete
```

## Project Structure

The planned project structure is:

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── smartcare/
    │           ├── controller/
    │           ├── entity/
    │           ├── exception/
    │           ├── repository/
    │           ├── service/
    │           └── SmartCareApplication.java
    │
    └── resources/
        └── application.properties
```

## Current Project Status

🚧 **Development in progress**

## Academic Purpose

This project is developed for educational purposes as part of the **CCS1303 Object-Oriented Programming coursework**.

All project work should follow university academic integrity requirements.
