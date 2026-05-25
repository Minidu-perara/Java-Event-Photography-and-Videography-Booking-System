# Java Event Photography and Videography Booking System

A Spring Boot web application for managing photography and videography bookings, built with clean code practices and student-friendly simplicity.

## Features

- **Dashboard**: Overview with 4 stat cards (clients, photographers, packages, bookings)
- **Client Management**: Create, view, edit, delete clients with email and phone validation
- **Photographer Management**: Track photographers by specialization, experience, and availability
- **Package Management**: Create photography and videography packages with pricing in Sri Lankan Rs.
- **Booking System**: Create and manage bookings with status tracking (Pending, Confirmed, Completed, Cancelled)
- **Sample Data**: Automatically loads sample data on first startup
- **Input Validation**: Jakarta validation with error display on all forms
- **Global Error Handling**: Centralized exception handling with user-friendly error page

## Tech Stack

- **Backend**: Spring Boot 3.x, Spring Data JPA, Spring MVC
- **Database**: H2 (in-memory), easily configurable to MySQL/PostgreSQL
- **Frontend**: Thymeleaf, Bootstrap 5
- **Validation**: Jakarta Validation (Hibernadate Validator)
- **Build**: Maven 3.9.6

## Project Structure

```
src/main/java/com/photobooking/
├── model/          # Entity classes with validation
├── repository/     # Spring Data JPA repositories
├── service/        # Business logic layer
├── controller/     # REST controllers & exception handling
└── config/         # Configuration & data initialization

src/main/resources/
├── templates/      # Thymeleaf HTML templates
│   ├── layout/     # Base template
│   ├── client/     # Client pages (list, form, view)
│   ├── photographer/  # Photographer pages
│   ├── package/    # Package pages
│   ├── booking/    # Booking pages
│   ├── index.html  # Dashboard
│   └── error.html  # Error page
└── application.properties  # Configuration
```

## Getting Started

### Prerequisites

- Java 24+ (or JDK from pom.xml)
- Maven 3.9+

### Build & Run

```bash
# Clone the repository
git clone https://github.com/Minidu-perara/Java-Event-Photography-and-Videography-Booking-System.git
cd Java-Event-Photography-and-Videography-Booking-System

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The app will start on **http://localhost:8080**

### Sample Data

On first startup, the application automatically loads:
- 3 clients (Ravi Silva, Priya Gunawardena, Amara Fernando)
- 3 photographers (Nimal Jayasundara, Chandra Wickramage, Dinesh Kapoor)
- 2 photography packages (Classic Wedding: Rs. 25,000 | Premium Wedding: Rs. 45,000)
- 2 sample bookings (one pending, one confirmed)

## Key Implementation Details

### Models
- **Person**: Abstract base class for Client and Photographer
- **BasePackage**: Abstract base for photography and videography packages
- **Booking**: Manages event bookings with status tracking
- All models include Jakarta validation annotations (@NotBlank, @Email, @Min, etc.)

### Controllers
- Clean separation of concerns with @ControllerAdvice for exception handling
- @Valid annotation on all POST methods for input validation
- Redirect on validation errors to preserve form state

### Templates
- **Student-friendly design**: Plain Bootstrap 5, no fancy animations or icons
- **Tables instead of cards**: List views use HTML tables for simplicity
- **Inline validation errors**: Red error messages appear below each form field
- **Responsive layout**: Mobile-friendly using Bootstrap grid system

### Data Initialization
- **DataInitializer** component implements CommandLineRunner
- Only loads sample data if tables are empty (checks `count() == 0`)
- Ensures idempotency across restarts

## API Endpoints

### Clients
- `GET /clients` - List all clients
- `GET /clients/{id}` - View single client
- `GET /clients/new` - Show new client form
- `POST /clients/new` - Create client
- `GET /clients/{id}/edit` - Edit form
- `POST /clients/{id}/edit` - Update client
- `POST /clients/{id}/delete` - Delete client

### Photographers
- `GET /photographers` - List all photographers
- `GET /photographers/{id}` - View single photographer
- `GET /photographers/new` - New photographer form
- `POST /photographers/new` - Create
- `GET /photographers/{id}/edit` - Edit form
- `POST /photographers/{id}/edit` - Update
- `POST /photographers/{id}/delete` - Delete

### Packages
- `GET /packages` - List all packages
- `GET /packages/{id}` - View package
- `GET /packages/new` - New package form
- `POST /packages/new` - Create
- `GET /packages/{id}/edit` - Edit form
- `POST /packages/{id}/edit` - Update
- `POST /packages/{id}/delete` - Delete

### Bookings
- `GET /bookings` - List bookings (optionally filter by status)
- `GET /bookings/{id}` - View booking
- `GET /bookings/new` - New booking form
- `POST /bookings/new` - Create
- `GET /bookings/{id}/edit` - Edit form
- `POST /bookings/{id}/edit` - Update
- `POST /bookings/{id}/status` - Update status
- `POST /bookings/{id}/delete` - Delete

### Dashboard
- `GET /` - Dashboard with stats and upcoming bookings

## Validation Rules

### Client
- First & Last Name: @NotBlank
- Email: @NotBlank, @Email
- Phone: @NotBlank
- Address: Optional

### Photographer
- First & Last Name: @NotBlank
- Email: @NotBlank, @Email
- Phone: @NotBlank
- Specialization: @NotBlank
- Years of Experience: @Min(0)
- Available: Boolean flag

### Package
- Name: @NotBlank
- Price: @NotNull, @DecimalMin(0.0, exclusive)
- Duration: @Min(1) hour
- Type: PHOTOGRAPHY, VIDEOGRAPHY, or BOTH

### Booking
- Event Type: @NotBlank
- Event Date: @NotNull, @FutureOrPresent
- Client, Photographer, Package: Required (no @NotNull, enforced via controller)
- Status: PENDING, CONFIRMED, COMPLETED, CANCELLED

## Error Handling

- Global **@ControllerAdvice** catches all exceptions
- Redirects to `/error` page with user-friendly message
- Flash attributes preserve error messages across redirects

## Code Style

- **Student-friendly code**: Simple, clear, multi-line logic
- **Inline comments**: "// get all clients from database" style
- **No clever one-liners**: Prioritize readability
- **Standard Spring patterns**: Repository, Service, Controller layers

## Future Enhancements

- Add photos/gallery to packages and bookings
- SMS/Email notifications for booking status
- Payment integration for booking deposits
- Calendar view for bookings
- Photographer availability calendar
- Review and rating system
- Invoice generation
- Receipt emails

## Author

Minidu Perara  
Email: vxgritnode@proton.me

## License

Open source - free to use and modify
