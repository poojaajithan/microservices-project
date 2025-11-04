# 🧩 Microservices Project

A modular **Spring Boot + Spring Cloud** microservices application secured with **OAuth2 (Auth0)** and integrated using **Eureka Service Discovery**, **API Gateway**, and **Feign Clients**.  
Each microservice is independently deployable and communicates securely using propagated JWT tokens.

---

## ⚙️ Architecture Overview

This project demonstrates a distributed microservices setup built with:

- **Spring Boot 3 / Java 17**
- **Spring Cloud 2023**
- **Spring Cloud Gateway**
- **Eureka Service Discovery**
- **Spring Security + OAuth2 / JWT (Auth0)**
- **Feign Clients / RestTemplate**
- **MySQL** for persistence

### 🧱 Services

| Service | Description | Port |
|----------|--------------|------|
| `api-gateway` | Routes requests, handles authentication and token relay | `8084` |
| `service-registry` | Eureka Discovery Server | `8761` |
| `user-service` | Manages user data; calls Rating service | `8081` |
| `rating-service` | Stores and retrieves ratings | `8082` |
| `hotel-service` | Provides hotel data | `8083` |

---

## 🔐 Security Flow

1. **User logs in via Auth0** → Gateway handles the OAuth2 authorization code flow.  
2. **Gateway** receives the JWT access token and forwards it in every downstream request.  
3. **Each microservice** validates the JWT (configured as an OAuth2 Resource Server).  
4. **User Service → Rating Service** calls propagate the JWT via Feign interceptor.  

This ensures secure, authenticated communication across all microservices.

---

## 🚀 Getting Started

### Prerequisites

- Java **17+**
- Maven **3.8+**
- MySQL (optional if DB is enabled)
- Auth0 account with:
  - An **API** (audience): `https://microservices-api`
  - A **Web App** (client) for OAuth2 login
  - Callback URL: `http://localhost:8084/login/oauth2/code/auth0`
  - Allowed logout URLs: `http://localhost:8084/`

---

### 🧭 Setup

### Step 1: Clone the repository

    git clone https://github.com/poojaajithan/microservices-project.git
    cd microservices-project

### Step 2: Configure Auth0
	1.	Log in to your Auth0 Dashboard → Applications → your Spring Boot App.
	2.	Under Settings, update the following:
	•	Allowed Callback URLs: http://localhost:8084/login/oauth2/code/auth0
	•	Allowed Logout URLs: http://localhost:8084/
	3.	Go to Applications → APIs and create a new API:
	•	Identifier (Audience): https://microservices-api
	•	Signing Algorithm: RS256
	4.	Copy the Domain (e.g. https://dev-xxxxxx.us.auth0.com).

  Then update each secured microservice’s `application.yml` with your Auth0 details:

    spring:
      security:
        oauth2:
          resourceserver:
            jwt:
              issuer-uri: https://dev-x1izhrf6b8cbnfh5.us.auth0.com/
              audience: https://microservices-api
          
### Step 3: Start the services

    Run each service in a separate terminal or from your IDE:

    Start Eureka Server
      cd service-registry
      mvn spring-boot:run

    Start API Gateway
      cd api-gateway
      mvn spring-boot:run

    Start User, Rating, and Hotel Services
      cd user-service/userservice
      mvn spring-boot:run

      cd rating-service/ratingservice
      mvn spring-boot:run
  
      cd hotel-service/hotelservice
      mvn spring-boot:run


### Step 4: Test the endpoints
	•	Open http://localhost:8084￼
	•	Authenticate via Auth0
	•	Access APIs through Gateway:
	•	http://localhost:8084/users
	•	http://localhost:8084/ratings
	•	http://localhost:8084/hotels

### 🧠 Token Propagation (Feign Interceptor)
    To ensure secure inter-service calls, JWT tokens are copied from incoming requests to outgoing Feign requests.
    
    @Configuration
    public class FeignClientInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null) {
                    template.header("Authorization", authHeader);
                }
            }
        }
    }

    This allows UserService to securely call RatingService with the same authenticated user context.

### 🧩 Tech Stack
	•	Spring Boot 3.3.x
	•	Spring Cloud 2023.0.x
	•	Auth0 OAuth2 / JWT
	•	Spring Security
	•	Spring Cloud Gateway
	•	Eureka Discovery
	•	Feign / RestTemplate
	•	MySQL + JPA
	•	Lombok
	•	Maven

### 🧪 Future Enhancements
	•	Add Order and Payment services
	•	Integrate Resilience4J for fault tolerance
	•	Implement Centralized Config Server
	•	Add Zipkin / OpenTelemetry tracing
	•	Dockerize with docker-compose

### 🧑‍💻 Contributing
    1.	Fork this repo
    2.	Create your feature branch
        git checkout -b feature/new-feature
    3.	Commit your changes
        git commit -m "Add new feature"
    4.	Push and create a Pull Request

✨ Build. Secure. Scale. Microservices with Spring Boot. ✨



