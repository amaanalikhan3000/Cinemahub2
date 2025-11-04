# Cinemahub 🎬


[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](http://makeapullrequest.com)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A backend movie booking platform built with Java Spring Boot, featuring secure authentication, seat management, and real-time booking validation using Redis caching and JWT-based authentication.


## 🚀 Overview

Cinemahub provides a scalable backend system for managing movie listings, user accounts, and ticket bookings. It focuses on performance, security, and reliability through modular design and well-defined REST APIs.

---

## 🧩 Features

- **User Authentication & Authorization**: Secure login and registration using Spring Security and JWT
- **Seat Booking System**: Real-time seat reservation with concurrency control
- **Redis Caching**: Enhanced performance through intelligent caching and session management
- **Email Verification**: OTP-based verification for secure booking confirmation
- **Environment Configuration**: Flexible setup via `application.yml`
- **Layered Architecture**: Clean separation with Controller, Service, and Repository patterns
- **CI/CD Ready**: Structured for seamless deployment and scalability testing

---

## ⚙️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Language** | Java 17+ |
| **Framework** | Spring Boot |
| **Database** | MySQL / PostgreSQL (JPA/Hibernate) |
| **Caching** | Redis |
| **Authentication** | Spring Security, JWT |
| **Build Tool** | Maven / Gradle |
| **Testing** | JUnit, Mockito |

---

## 🗂️ Project Structure

```
cinemahub/
├── src/
│   ├── main/
│   │   ├── java/com/cinemahub/
│   │   │   ├── controller/     # REST API endpoints
│   │   │   ├── service/        # Business logic layer
│   │   │   ├── repository/     # Data access layer (JPA)
│   │   │   ├── model/          # Entity definitions
│   │   │   └── config/         # Security, Redis, Mail configs
│   │   └── resources/
│   │       ├── application.yml # Application configuration
│   │       └── templates/      # Email templates
│   └── test/                   # Unit and integration tests
├── pom.xml / build.gradle      # Dependency management
├── CONTRIBUTING.md             # Contribution guidelines
├── CODE_OF_CONDUCT.md          # Code of conduct
└── README.md
```

---

## 🔐 Environment Configuration

Update the following values in your `application.yml` or environment variables before running:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cinemahub
    username: root
    password: your_password
  
  redis:
    host: localhost
    port: 6379

jwt:
  secret: your_jwt_secret
  expiration: 86400000  # 24 hours in milliseconds

mail:
  host: smtp.example.com
  username: your_email@example.com
  password: your_email_password
```

---

## 🏃‍♂️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven or Gradle
- MySQL or PostgreSQL
- Redis server

### Installation

1. **Fork and Clone the repository**
   ```bash
   # Fork the repo on GitHub first, then:
   git clone https://github.com/YOUR-USERNAME/cinemahub.git
   cd cinemahub
   ```

2. **Configure the environment**
   - Set up MySQL and Redis
   - Update credentials in `application.yml`

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   - Base URL: `http://localhost:8080/api/v1`
   - Swagger UI (if enabled): `http://localhost:8080/swagger-ui/`

---

## 📡 API Endpoints

### Authentication
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/verify-otp` - OTP verification

### Movies
- `GET /api/v1/movies` - List all movies
- `GET /api/v1/movies/{id}` - Get movie details

### Bookings
- `POST /api/v1/bookings` - Create new booking
- `GET /api/v1/bookings/{id}` - Get booking details
- `DELETE /api/v1/bookings/{id}` - Cancel booking

---

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

Run with coverage:

```bash
mvn clean test jacoco:report
```


## 🤝 Contributing

We love contributions! Whether you're a beginner or an experienced developer, there's something for everyone.

### 📋 How to Contribute

1. **Check out open issues** - Look for issues labeled `good first issue`, `hacktoberfest`, or `help wanted`
2. **Comment on the issue** - Let us know you're working on it
3. **Fork the repository** - Create your own copy
4. **Create a branch** - `git checkout -b feature/your-feature-name`
5. **Make your changes** - Write clean, documented code
6. **Test your changes** - Ensure all tests pass
7. **Commit your changes** - `git commit -m 'Add some feature'`
8. **Push to your fork** - `git push origin feature/your-feature-name`
9. **Create a Pull Request** - Submit your PR with a clear description

### 📝 Contribution Guidelines

- Read [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines
- Follow the existing code style and conventions
- Write meaningful commit messages
- Add tests for new features
- Update documentation as needed
- Be respectful and follow our [Code of Conduct](CODE_OF_CONDUCT.md)



## 💬 Community & Support

- **Questions?** Open a [Discussion](https://github.com/yourusername/cinemahub/discussions)
- **Bug Reports:** Use the [Issue Tracker](https://github.com/yourusername/cinemahub/issues)
- **Feature Requests:** Open an issue with the `enhancement` label

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Maintainers

- **[@amaanalikhan3000](https://github.com/amaanalikhan3000)**


---

## 📧 Contact

For questions or support, reach out at: amaanali.amaanali@gmail.com
---
## ⭐ Show Your Support
If you find this project helpful, please consider giving it a ⭐️ on GitHub!
---

## 🙏 Acknowledgments
- Spring Boot documentation
- Redis community
- All our amazing contributors
- Hacktoberfest organizers

