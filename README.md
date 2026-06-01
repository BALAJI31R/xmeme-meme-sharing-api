# XMeme - Meme Sharing API

A RESTful API for sharing memes built with Spring Boot and MongoDB. Users can post memes with a name, caption, and image URL, browse the latest memes, and retrieve individual memes by ID.

## Overview

XMeme provides a simple meme-sharing backend with duplicate detection, input validation, and paginated listing of the most recent memes.

## Tech Stack

- Java 11
- Spring Boot 2.7.1
- Spring Data MongoDB
- Spring Data Redis
- Spring AMQP
- Lombok
- Spring Validation
- Gradle
- Spotbugs, Checkstyle, Jacoco (code quality)
- Docker

## Project Structure

```
src/main/java/com/crio/starter/
├── App.java                        # Spring Boot entry point
├── controller/
│   └── MemeController.java         # REST endpoints
├── data/
│   └── Meme.java                   # MongoDB document model
├── exchange/
│   ├── CreateMemeRequestDto.java   # Create request DTO
│   ├── CreateMemeResponseDto.java  # Create response DTO
│   └── MemeResponseDto.java        # Get response DTO
├── repository/
│   └── MemeRepository.java         # MongoDB repository
└── service/
    └── MemeService.java            # Business logic
```

## API Endpoints

| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|--------------|
| POST | `/memes` | Create a new meme | 201, 400, 409 |
| GET | `/memes` | Get latest 100 memes | 200 |
| GET | `/memes/{id}` | Get a meme by ID | 200, 404 |

### Create Meme Request

```json
{
  "name": "Author Name",
  "caption": "Meme caption text",
  "url": "https://example.com/meme.jpg"
}
```

## Features

- Duplicate detection (same name + caption + url returns 409 Conflict)
- Input validation (name, caption, url required and non-blank)
- Latest 100 memes returned in reverse chronological order
- Code quality enforcement with Spotbugs, Checkstyle, and Jacoco

## Build & Run

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Run tests
./gradlew test

# Generate code coverage report
./gradlew jacocoTestReport
```

## Prerequisites

- MongoDB
- Redis (optional)

## Author

Balaji R — Crio.Do
