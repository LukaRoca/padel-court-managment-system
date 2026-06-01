# Padel Court Management System

Design and development of a complete API and Single Page Application (SPA) for managing and renting Padel courts. Built as a 3-person team project, this system provides a seamless experience for finding clubs, checking court availability, and managing bookings.

## Features

* **Single Page Application (SPA):** Custom frontend built with Vanilla JavaScript using a custom DOM-manipulation DSL, ensuring fast and dynamic content rendering without page reloads.
* **RESTful API:** Robust backend architecture to manage complex entities (Users, Clubs, Courts, and Rentals).
* **Advanced Search & Filtering:** Complex filtering logic verifying real-time time availability by date, club, and specific courts.
* **Security (JWT):** Protection of sensitive endpoints and user sessions through JSON Web Tokens (JWT Bearer Auth).
* **Responsive Design:** Clean and accessible UI utilizing Bootstrap 5.

## Technologies & Stack

**Back-end & API:**
* Kotlin (with `http4k` framework)
* OpenAPI 3.0 / Swagger (API Specification & Documentation)
* Node.js

**Front-end:**
* JavaScript (ES6 Modules)
* HTML5 / CSS3
* Bootstrap 5

**Infrastructure & Database:**
* PostgreSQL
* Docker
* Gradle (Build Tool)

## Prerequisites

Before running this project, ensure you have the following installed:
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (for the database)
* [Java JDK 21+](https://adoptium.net/)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Recommended)

## How to Run

### 1. Database Setup (via Docker)
Start by spinning up the PostgreSQL database in a Docker container. We use port `5433` to avoid conflicts with existing local databases.

```bash
docker run --name sports-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=sports -p 5433:5432 -d postgres:16
```

### 2. Environment Variables
The backend requires a connection string to communicate with the database. Set the following environment variable in your system or your IDE (Run Configurations):

#### Name: JDBC_DATABASE_URL **

#### Value: jdbc:postgresql://localhost:5433/sports?user=postgres&password=postgres 

### 3. Database Schema Initialization
Before launching the server, you need to create the tables and insert mock data.
Using your preferred database client (e.g., IntelliJ Database tool or pgAdmin), connect to the database using the credentials above and execute the following SQL scripts located in src/main/sql/:

#### Execute createSchema.sql

#### Execute addData.sql

### 4. Running the Server
You can run the server directly via your IDE by executing the main function in Server.kt, or via the terminal using Gradle:

### 5. Accessing the Application
   Once the server is running, open your web browser and navigate to:

#### http://localhost:8080/


## API Documentation
The API is fully specified using OpenAPI 3.0. The specification details all available endpoints, request/response formats, and security schemas required to interact with the Users, Clubs, Courts, and Rentals entities.

#### Authors
 
Afonso Santos

Bernardo Jaco

Luka Roca