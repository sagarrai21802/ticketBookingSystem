# Indian Train Ticket Booking System - Project Documentation

## Overview
This is a Spring Boot-based REST API for an Indian Railway ticket booking system, similar to IRCTC. It provides functionalities for user authentication, train search, ticket booking, payment processing, and booking management.

## Technologies Used
- **Java 17**: Programming language
- **Spring Boot 3.2.1**: Framework for building the application
- **Spring Web**: For REST API endpoints
- **Spring Data JPA**: For database operations
- **Spring Security**: For authentication and authorization
- **JWT (JSON Web Tokens)**: For stateless authentication
- **H2 Database**: In-memory database for development
- **MySQL**: Optional for production
- **Lombok**: For reducing boilerplate code
- **Swagger/OpenAPI**: For API documentation
- **Gradle**: Build tool
- **JUnit**: For testing

## How to Run the Application
```bash
./gradlew bootRun
```
The application will start on `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`

## Project Hierarchy

```
src/main/java/com/irctc/booking/
├── TicketBookingApplication.java          # Main application entry point
├── config/
│   ├── JwtAuthenticationFilter.java       # JWT authentication filter
│   └── SecurityConfig.java                # Security configuration
├── controller/
│   ├── AuthController.java                # Authentication endpoints
│   ├── BookingController.java             # Booking management endpoints
│   ├── PaymentController.java             # Payment processing endpoints
│   ├── StationController.java             # Station information endpoints
│   └── TrainController.java               # Train search endpoints
├── dto/
│   ├── request/                           # Request DTOs
│   │   ├── BookingRequest.java
│   │   ├── LoginRequest.java
│   │   ├── PassengerRequest.java
│   │   ├── PaymentRequest.java
│   │   ├── RegisterRequest.java
│   │   └── TrainSearchRequest.java
│   └── response/                          # Response DTOs
│       ├── ApiResponse.java
│       ├── AuthResponse.java
│       ├── BookingResponse.java
│       └── TrainSearchResponse.java
├── exception/
│   ├── BookingException.java              # Custom booking exceptions
│   ├── GlobalExceptionHandler.java        # Global exception handling
│   ├── PaymentException.java              # Payment-related exceptions
│   └── ResourceNotFoundException.java     # Resource not found exceptions
├── model/
│   ├── BerthPreference.java               # Enum for berth preferences
│   ├── BerthType.java                     # Enum for berth types
│   ├── Booking.java                       # Booking entity
│   ├── BookingStatus.java                 # Enum for booking status
│   ├── CoachClass.java                    # Enum for coach classes
│   ├── ConcessionCategory.java            # Enum for concessions
│   ├── FoodPreference.java                # Enum for food preferences
│   ├── Gender.java                        # Enum for gender
│   ├── IdProofType.java                   # Enum for ID proof types
│   ├── Passenger.java                     # Passenger entity
│   ├── PassengerStatus.java               # Enum for passenger status
│   ├── Payment.java                       # Payment entity
│   ├── PaymentMethod.java                 # Enum for payment methods
│   ├── PaymentStatus.java                 # Enum for payment status
│   ├── QuotaType.java                     # Enum for quota types
│   ├── Station.java                       # Station entity
│   ├── Train.java                         # Train entity
│   ├── TrainSchedule.java                 # Train schedule entity
│   ├── TrainType.java                     # Enum for train types
│   └── User.java                          # User entity
├── repository/
│   ├── BookingRepository.java             # Booking data access
│   ├── PassengerRepository.java           # Passenger data access
│   ├── PaymentRepository.java             # Payment data access
│   ├── StationRepository.java             # Station data access
│   ├── TrainRepository.java               # Train data access
│   ├── TrainScheduleRepository.java       # Train schedule data access
│   └── UserRepository.java                # User data access
├── service/
│   ├── BookingService.java                # Booking business logic
│   ├── PaymentService.java                # Payment business logic
│   ├── StationService.java                # Station business logic
│   ├── TrainService.java                  # Train business logic
│   └── UserService.java                   # User business logic
└── util/
    ├── JwtUtil.java                       # JWT utility functions
    └── PnrGenerator.java                  # PNR generation utility
```

## File Responsibilities

### Main Application
- **TicketBookingApplication.java**: Entry point with main method, configures Swagger

### Configuration
- **SecurityConfig.java**: Configures Spring Security, JWT authentication, public/private endpoints
- **JwtAuthenticationFilter.java**: Processes JWT tokens in requests

### Controllers (REST Endpoints)
- **AuthController.java**: Handles login, registration, user profile
- **BookingController.java**: Manages booking creation, retrieval, cancellation
- **PaymentController.java**: Handles payment processing
- **StationController.java**: Provides station information
- **TrainController.java**: Handles train search and details

### Services (Business Logic)
- **BookingService.java**: Core booking logic, fare calculation, seat allocation
- **PaymentService.java**: Payment processing and validation
- **StationService.java**: Station data management
- **TrainService.java**: Train search and schedule management
- **UserService.java**: User authentication and management

### Repositories (Data Access)
- Interfaces extending JpaRepository for database operations on each entity

### Models (Entities)
- JPA entities representing database tables with relationships

### DTOs
- Request/Response objects for API communication

### Exceptions
- Custom exceptions and global exception handler

### Utils
- Helper classes for JWT handling and PNR generation

## How the System Works

1. **User Registration/Login**: Users register and authenticate using JWT tokens
2. **Train Search**: Users search for trains between stations on specific dates
3. **Booking Creation**: Users create bookings with passenger details, fare calculation
4. **Payment Processing**: Bookings are confirmed after successful payment
5. **Booking Management**: Users can view, cancel bookings
6. **PNR Status**: Public access to check booking status by PNR

## Detailed Code Explanations

### TicketBookingApplication.java
```java
@SpringBootApplication  // Enables auto-configuration, component scanning, configuration
@OpenAPIDefinition(...) // Configures Swagger/OpenAPI documentation
public class TicketBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketBookingApplication.class, args); // Starts the application
        // Prints startup information
    }
}
```

### SecurityConfig.java
```java
@Configuration
@EnableWebSecurity  // Enables Spring Security
@EnableMethodSecurity  // Enables @PreAuthorize, @Secured annotations
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // Injects JWT filter

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disables CSRF for stateless API
            .authorizeHttpRequests(auth -> auth
                // Public endpoints (no authentication required)
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/stations/**").permitAll()
                .requestMatchers("/api/trains/search/**").permitAll()
                .requestMatchers("/api/trains/{id}").permitAll()
                .requestMatchers("/api/bookings/pnr-status/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated() // All other requests require authentication
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions, JWT only
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin()) // Allows H2 console in frames
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adds JWT filter
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password hashing
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Authentication manager bean
    }
}
```

### BookingController.java
```java
@RestController  // Marks as REST controller
@RequestMapping("/api/bookings")  // Base path for all endpoints
@Tag(name = "Bookings", description = "Ticket booking APIs")  // Swagger tag
public class BookingController {

    @Autowired
    private BookingService bookingService; // Injects booking service

    @PostMapping  // POST /api/bookings
    @SecurityRequirement(name = "bearerAuth")  // Requires JWT authentication
    @Operation(summary = "Create booking", description = "Book train tickets")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request,  // Validates and deserializes request body
            @AuthenticationPrincipal UserDetails userDetails) {  // Gets authenticated user
        
        BookingResponse response = bookingService.createBooking(request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Booking created successfully. Please complete payment.", response));
    }

    @GetMapping("/pnr-status/{pnr}")  // GET /api/bookings/pnr-status/{pnr}
    @Operation(summary = "Check PNR status", description = "Get booking details by PNR number (public)")
    public ResponseEntity<ApiResponse<BookingResponse>> getPnrStatus(@PathVariable String pnr) {
        BookingResponse response = bookingService.getBookingByPnr(pnr);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/my-bookings")  // GET /api/bookings/my-bookings
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get my bookings", description = "Get all bookings for logged in user")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getMyBookings(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        List<BookingResponse> bookings = bookingService.getUserBookings(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    @PostMapping("/{pnr}/cancel")  // POST /api/bookings/{pnr}/cancel
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cancel booking", description = "Cancel a booking and get refund")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(
            @PathVariable String pnr,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        BookingResponse response = bookingService.cancelBooking(pnr, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled. Refund will be processed.", response));
    }
}
```

### BookingService.java (Key Methods)

#### createBooking Method
```java
@Transactional  // Ensures atomicity
public BookingResponse createBooking(BookingRequest request, String username) {
    // Get user from database
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    // Validate train exists
    Train train = trainRepository.findByTrainNumber(request.getTrainNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Train", "trainNumber", request.getTrainNumber()));

    // Validate source and destination stations
    Station source = stationRepository.findByStationCodeIgnoreCase(request.getSourceStationCode())
            .orElseThrow(() -> new ResourceNotFoundException("Station", "code", request.getSourceStationCode()));
    Station dest = stationRepository.findByStationCodeIgnoreCase(request.getDestinationStationCode())
            .orElseThrow(() -> new ResourceNotFoundException("Station", "code", request.getDestinationStationCode()));

    // Validate journey date is not in past
    if (request.getJourneyDate().isBefore(LocalDate.now())) {
        throw new BookingException("Journey date cannot be in the past");
    }

    // Calculate distance between stations
    int distance = calculateDistance(train.getTrainNumber(), source.getStationCode(), dest.getStationCode());
    
    // Calculate base fare based on coach class, distance, and passenger count
    BigDecimal baseFare = calculateFare(train, request.getCoachClass(), distance, request.getPassengers().size());

    // Generate unique PNR
    String pnr;
    do {
        pnr = PnrGenerator.generatePnr();
    } while (bookingRepository.existsByPnrNumber(pnr));

    // Calculate additional charges
    BigDecimal reservationCharge = BigDecimal.valueOf(40 * request.getPassengers().size()); // ₹40 per passenger
    BigDecimal serviceTax = baseFare.multiply(BigDecimal.valueOf(0.05)).setScale(0, RoundingMode.HALF_UP); // 5% service tax
    BigDecimal totalFare = baseFare.add(reservationCharge).add(serviceTax);

    // Create booking entity
    Booking booking = Booking.builder()
            .pnrNumber(pnr)
            .user(user)
            .train(train)
            .sourceStation(source)
            .destinationStation(dest)
            .journeyDate(request.getJourneyDate())
            .coachClass(request.getCoachClass())
            .quotaType(request.getQuotaType())
            .status(BookingStatus.PENDING) // Initially pending, confirmed after payment
            .baseFare(baseFare)
            .reservationCharge(reservationCharge)
            .serviceTax(serviceTax)
            .totalFare(totalFare)
            .distanceKm(distance)
            .contactEmail(request.getContactEmail())
            .contactMobile(request.getContactMobile())
            .passengers(new ArrayList<>())
            .build();

    // Add passengers to booking
    for (PassengerRequest pReq : request.getPassengers()) {
        Passenger passenger = Passenger.builder()
                .booking(booking)
                .name(pReq.getName())
                .age(pReq.getAge())
                .gender(pReq.getGender())
                .berthPreference(pReq.getBerthPreference())
                .foodPreference(pReq.getFoodPreference())
                .idProofType(pReq.getIdProofType())
                .idProofNumber(pReq.getIdProofNumber())
                .concessionCategory(pReq.getConcessionCategory())
                .nationality(pReq.getNationality())
                .status(PassengerStatus.CONFIRMED)
                .build();
        booking.getPassengers().add(passenger);
    }

    // Save booking to database
    Booking saved = bookingRepository.save(booking);

    // Convert to response DTO
    return mapToBookingResponse(saved);
}
```

#### cancelBooking Method
```java
@Transactional
public BookingResponse cancelBooking(String pnr, String username) {
    // Find booking by PNR
    Booking booking = bookingRepository.findByPnrNumber(pnr)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "PNR", pnr));

    // Verify user owns the booking
    if (!booking.getUser().getUsername().equals(username)) {
        throw new BookingException("You can only cancel your own bookings");
    }

    // Check if already cancelled
    if (booking.getStatus() == BookingStatus.CANCELLED) {
        throw new BookingException("Booking is already cancelled");
    }

    // Check if journey date has passed
    if (booking.getJourneyDate().isBefore(LocalDate.now())) {
        throw new BookingException("Cannot cancel past bookings");
    }

    // Calculate refund based on days to journey
    BigDecimal refund = calculateRefund(booking);

    // Update booking status
    booking.setStatus(BookingStatus.CANCELLED);
    booking.setCancelledAt(LocalDateTime.now());
    booking.setRefundAmount(refund);

    // Update passenger statuses
    for (Passenger passenger : booking.getPassengers()) {
        passenger.setStatus(PassengerStatus.CANCELLED);
    }

    // Save changes
    Booking saved = bookingRepository.save(booking);
    return mapToBookingResponse(saved);
}
```

#### Helper Methods

##### calculateFare Method
```java
private BigDecimal calculateFare(Train train, CoachClass coachClass, int distance, int passengers) {
    // Get fare rate based on coach class
    BigDecimal rate = switch (coachClass) {
        case FIRST_AC -> train.getFareRate1A();
        case SECOND_AC -> train.getFareRate2A();
        case THIRD_AC -> train.getFareRate3A();
        case SLEEPER -> train.getFareRateSL();
        case CHAIR_CAR -> train.getFareRateCC();
        case SECOND_SITTING -> train.getFareRate2S();
        default -> BigDecimal.valueOf(1.0);
    };
    // Calculate total fare: rate * distance * number of passengers
    return rate.multiply(BigDecimal.valueOf(distance * passengers));
}
```

##### calculateRefund Method
```java
private BigDecimal calculateRefund(Booking booking) {
    // Calculate days until journey
    long daysToJourney = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), booking.getJourneyDate());
    
    // Determine refund percentage based on cancellation policy
    BigDecimal refundPercent;
    if (daysToJourney > 7) {
        refundPercent = BigDecimal.valueOf(0.95); // 95% refund (>7 days)
    } else if (daysToJourney > 2) {
        refundPercent = BigDecimal.valueOf(0.75); // 75% refund (3-7 days)
    } else if (daysToJourney > 0) {
        refundPercent = BigDecimal.valueOf(0.50); // 50% refund (1-2 days)
    } else {
        refundPercent = BigDecimal.ZERO; // No refund (same day)
    }
    
    // Calculate refund amount
    return booking.getTotalFare().multiply(refundPercent).setScale(0, RoundingMode.HALF_UP);
}
```

##### allocateBerth Method
```java
private BerthType allocateBerth(BerthPreference preference, int seatNumber) {
    // Allocate berth based on passenger preference
    return switch (preference) {
        case LOWER -> BerthType.LOWER;
        case MIDDLE -> BerthType.MIDDLE;
        case UPPER -> BerthType.UPPER;
        case SIDE_LOWER -> BerthType.SIDE_LOWER;
        case SIDE_UPPER -> BerthType.SIDE_UPPER;
        // Default allocation based on seat number
        default -> seatNumber % 3 == 1 ? BerthType.LOWER : 
                   seatNumber % 3 == 2 ? BerthType.MIDDLE : BerthType.UPPER;
    };
}
```

## Database Schema
The application uses JPA entities with relationships:
- User (1) -> (*) Booking
- Booking (1) -> (*) Passenger
- Booking (1) -> (1) Train
- Booking (1) -> (1) Source Station
- Booking (1) -> (1) Destination Station
- Train (1) -> (*) TrainSchedule
- TrainSchedule (1) -> (1) Station

## API Endpoints
- POST /api/auth/register - User registration
- POST /api/auth/login - User login
- GET /api/trains/search - Search trains
- POST /api/bookings - Create booking
- GET /api/bookings/my-bookings - Get user bookings
- POST /api/bookings/{pnr}/cancel - Cancel booking
- GET /api/bookings/pnr-status/{pnr} - Check PNR status
- POST /api/payments - Process payment

## Security Features
- JWT-based authentication
- Password encryption with BCrypt
- Role-based access control (USER, ADMIN)
- CSRF protection disabled for stateless API
- Public endpoints for search and PNR status

## Business Rules
- Bookings can only be cancelled before journey date
- Refund amounts vary based on cancellation timing
- PNR numbers are unique 10-character alphanumeric codes
- Fare calculation considers distance, coach class, and passenger count
- Seat allocation considers berth preferences