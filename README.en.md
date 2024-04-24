# AIDatasetsPlatform

## Overview

AIDatasetsPlatform is a comprehensive Spring Boot-based application designed to collect, manage, and utilize datasets for Artificial Intelligence (AI) research and development. It offers a robust platform for users to upload, store, and access diverse datasets ranging from text and images to audio and video. The platform aims to facilitate the rapid development and testing of AI models by providing a rich repository of data.

## Features

- **Dataset Management**: Users can upload, download, and manage datasets. Each dataset is described with metadata including names and descriptions.
- **User Authentication**: The platform supports user registration and authentication, ensuring secure access to the system.
- **File Storage**: Utilizes Apache Commons IO for efficient file handling and storage operations.
- **Security**: Spring Boot security is leveraged for secure access to the API endpoints, with support for basic authentication and role-based access control.
- **Database Support**: Integration with MySQL for persistent storage of user and dataset information.

## Technology Stack

- **Backend**: Spring Boot, Spring Data JPA, Spring Security
- **Database**: MySQL
- **Frontend**: Thymeleaf templates for rendering UI components
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- JDK 17 or higher
- MySQL Server (5.7 or higher)
- Maven

### Setup

1. **Clone the repository:**

```sh
git clone https://github.com/uluckystar/AIDatasetsPlatform.git
cd AIDatasetsPlatform
```

2. **Configure MySQL Database:**

Create a database named `adp` and update the `src/main/resources/application.properties` file with your MySQL user and password.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/adp?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=12345
```

3. **Build the project:**

```sh
mvn clean install
```

4. **Run the application:**

```sh
java -jar target/AIDatasetsPlatform-0.0.1-SNAPSHOT.jar
```

The server will start on `http://localhost:8088`.

## Usage

- Visit `http://localhost:8088/login` to access the login page.
- After login, users can upload datasets by navigating to the `/file-manager` endpoint.
- Datasets can be downloaded from the dataset listing page.

## Contribution

Contributions are welcome. Please fork the repository and submit pull requests with any enhancements.

## License

[MIT License](LICENSE)

---

Please adjust the URLs, database configuration, and any specific instructions as per your project's actual setup and requirements.