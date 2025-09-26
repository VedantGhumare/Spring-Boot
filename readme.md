## Challenges Faced By Spring Framework:
1. Extensive Configuration
2. Complex Dependency Management
3. Steep Learning Curve
---

## Why Spring Boot?
1. Auto-Configuration
2. Starter Dependencies
3. Embedded Servers
4. Microservices Support
5. Actuator
6. Metrics and Health Checks
---

## Core Principles :
1. Beans
2. Inversion of Control (IoC)
3. Dependency Injection (DI)
4. Aspect-Oriented Programming (AOP)
---
## Beans:
- Objects managed by the Spring IoC container.
- Spring takes care of their lifecycle (creation, wiring/dependency injection, initialization, destruction).
- Configured via XML, annotations, or Java configuration classes.
- Singleton scope by default, but can be configured to other scopes (prototype, request, session, etc.).
- Stored in the Spring ApplicationContext.

---
## Inversion of Control (IoC):
- The control of object creation and management is transferred from the application code to the Spring container.
- The container is responsible for instantiating, configuring, and managing the lifecycle of objects.
- This promotes loose coupling and enhances testability.
- Example: Instead of creating an instance of a service class directly, you define it in the Spring configuration, and the container injects it where needed.
- Benefits:
  - Decouples components
  - Enhances modularity
  - Facilitates easier testing

---
## Dependency Injection (DI):
- A design pattern where an object's dependencies are provided by an external entity (the Spring container) rather than the object creating them itself.
- Types of DI:
  - Constructor Injection
  - Setter Injection
  - Field Injection
```java
interface NotificationService {
    void sendNotification(String message);
}

class UserController {

    private final NotificationService constructorInjectedService;
    private NotificationService setterInjectedService;

    // 1. Constructor Injection (Best Practice as we can set it as final)
    public UserController(NotificationService notificationService) {
        this.constructorInjectedService = notificationService;
    }

    // 2. Setter Injection
    public void setNotificationService(NotificationService notificationService) {
        this.setterInjectedService = notificationService;
    }

    // 3. Field Injection
    @Autowired
    private NotificationService fieldInjectedService;
}
```
--- 