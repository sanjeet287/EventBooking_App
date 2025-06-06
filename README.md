# EventBooking_App
#  Event Booking App - Microservices Architecture

A full-stack Event Booking Platform built with Microservices architecture, supporting event discovery, ticket booking, secure payments with Stripe, and email notifications.

---

## 🛠 Tech Stack

### Backend
- **Java 17**, **Spring Boot**
- **Microservices** (Event, Booking, Payment, Notification, User)
- **RabbitMQ** for asynchronous communication
- **Redis** for caching
- **PostgreSQL** for database
- **Spring Cloud Gateway** as API Gateway
- **Eureka** for service discovery
- **Feign Client** for internal service-to-service calls (where applicable)
- **Spring Security with JWT**
- **Prometheus + Grafana** for monitoring
- **Spring Boot Actuator** for health checks
- **Thymeleaf** for HTML email templating

### Frontend
- **React.js**
- Axios for API calls
- Tailwind CSS for styling



## 🔧 Microservices Overview

###  1. Event-Service
- Manage events & categories
- CRUD operations for events
- Caching with Redis
- JWT protected routes
- Validations & Global Exception Handling

###  2. Booking-Service
- Book, update or cancel tickets
- Communicates with Event & Payment services
- RabbitMQ integration for status updates

###  3. Payment-Service
- Stripe payment gateway integration
- Transaction handling
- Sends payment success/failure to Booking via RabbitMQ


##  API Gateway
- Routes requests to microservices
- JWT Token validation filter
- Rate Limiting support
- Service Discovery via Eureka



##  Monitoring & Observability
- Spring Boot Actuator (per service)
- Prometheus scraping metrics
- Grafana dashboards for visualization
- Custom health indicators



##  Running the App

### Prerequisites:
- Java 17+
- Maven
- RabbitMQ, Redis, PostgreSQL
- React.js (for frontend)

### Start RabbitMQ & Redis 

