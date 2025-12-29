# 🚂 Indian Train Ticket Booking System

A complete REST API backend for train ticket booking, similar to IRCTC (Indian Railway Catering and Tourism Corporation).

## 📋 Features

- **User Management**: Registration, Login with JWT authentication
- **Station Search**: Search Indian railway stations by name, code, or city
- **Train Search**: Search trains between stations with seat availability
- **Ticket Booking**: Book tickets with PNR generation
- **Multiple Coach Classes**: 1A, 2A, 3A, SL, CC, 2S support
- **Payment Processing**: Mock payment gateway integration
- **Booking Cancellation**: Cancel with automatic refund calculation
- **PNR Status**: Check booking status anytime

## 🛠 Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Security** with JWT
- **Spring Data JPA**
- **H2 Database** (embedded, can switch to MySQL)
- **Gradle 8.5**
- **Swagger/OpenAPI** for API documentation
- **Lombok** for reducing boilerplate

## 📁 Project Structure

```
src/main/java/com/irctc/booking/
├── TicketBookingApplication.java     # Main application
├── config/                            # Security & JWT configuration
│   ├── SecurityConfig.java
│   └── JwtAuthenticationFilter.java
├── controller/                        # REST API endpoints
│   ├── AuthController.java
│   ├── StationController.java
│   ├── TrainController.java
│   ├── BookingController.java
│   └── PaymentController.java
├── service/                           # Business logic
│   ├── UserService.java
│   ├── StationService.java
│   ├── TrainService.java
│   ├── BookingService.java
│   └── PaymentService.java
├── repository/                        # Data access layer
├── model/                             # Entity classes
├── dto/                               # Request/Response DTOs
├── exception/                         # Exception handling
└── util/                              # Utilities (JWT, PNR generator)
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Gradle (or use included wrapper)

### Run the Application

```bash
# Navigate to project directory
cd "Ticket Booking System"

# Using Gradle wrapper
./gradlew bootRun

# Or build and run JAR
./gradlew build
java -jar build/libs/ticket-booking-system-1.0.0.jar
```

### Access Points

| URL | Description |
|-----|-------------|
| http://localhost:8080 | Application |
| http://localhost:8080/swagger-ui.html | API Documentation |
| http://localhost:8080/h2-console | Database Console |

### H2 Database Console
- **JDBC URL**: `jdbc:h2:mem:irctcdb`
- **Username**: `sa`
- **Password**: (leave empty)

## 📡 API Endpoints

### Authentication (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |

### Stations (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/stations` | Get all stations |
| GET | `/api/stations/search?query=delhi` | Search stations |
| GET | `/api/stations/code/NDLS` | Get station by code |

### Trains
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/trains/search?from=NDLS&to=BCT&date=2025-01-15` | Search trains |
| GET | `/api/trains/{trainNumber}/route` | Get train route |

### Bookings (Authenticated)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bookings` | Create booking |
| GET | `/api/bookings/pnr-status/{pnr}` | Check PNR status |
| GET | `/api/bookings/my-bookings` | Get user's bookings |
| POST | `/api/bookings/{pnr}/cancel` | Cancel booking |

### Payments (Authenticated)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/payments/process` | Process payment |
| POST | `/api/payments/refund/{pnr}` | Process refund |

## 🔐 Authentication

Use JWT token in Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

### Sample Users (Pre-loaded)
| Username | Password | Role |
|----------|----------|------|
| testuser | password123 | USER |
| admin | admin123 | ADMIN |

## 📝 Example API Usage

### 1. Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "rahul",
    "password": "password123",
    "fullName": "Rahul Sharma",
    "email": "rahul@example.com",
    "mobileNumber": "9876543210"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "rahul",
    "password": "password123"
  }'
```

### 3. Search Trains
```bash
curl "http://localhost:8080/api/trains/search?from=NDLS&to=BCT&date=2025-01-15"
```

### 4. Book Ticket
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "trainNumber": "12951",
    "sourceStationCode": "NDLS",
    "destinationStationCode": "BCT",
    "journeyDate": "2025-01-15",
    "coachClass": "THIRD_AC",
    "passengers": [
      {
        "name": "Rahul Sharma",
        "age": 28,
        "gender": "MALE",
        "berthPreference": "LOWER"
      }
    ],
    "contactEmail": "rahul@example.com",
    "contactMobile": "9876543210"
  }'
```

## 🚆 Sample Data

The application comes pre-loaded with:
- **20 major Indian railway stations** (NDLS, BCT, HWH, MAS, SBC, etc.)
- **10 popular trains** (Rajdhani, Shatabdi, Duronto, Express)
- **Train schedules** with routes and timings
- **Test users** for immediate testing

## 📚 Coach Classes

| Code | Class Name | Description |
|------|------------|-------------|
| 1A | First AC | Premium air-conditioned |
| 2A | Second AC | Two-tier AC sleeper |
| 3A | Third AC | Three-tier AC sleeper |
| SL | Sleeper | Non-AC sleeper |
| CC | Chair Car | AC seating |
| 2S | Second Sitting | Non-AC seating |

## 🔧 Configuration

Edit `application.properties` to:
- Change port: `server.port=8080`
- Switch to MySQL (configuration provided)
- Modify JWT settings

## 📄 License

This is an educational project for learning purposes.

---
Built with ❤️ for Indian Railways enthusiasts
