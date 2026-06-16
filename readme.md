## 🛒 E-Commerce Backend

The purpose of this project was to build a strong backend application for the e-commerce project using **SOLID** principles and relying on a **Modular monolith + Hexagonal** architectural style that can be scalable and migrated to microservices in case that is needed.

---

### 🛠️ Tech Stack & Justification

### Java + SpringBoot

Java is a robust, statically typed language that will ensure performance and safety, combined with Spring Boot's powerful ecosystem for enterprise grade security, and rapid development.

### PostgreSQL + JPA/Hibernate

Relational databases are the industry standard for handling transactional data like, inventory, users, and orders using ACID. Hibernate will act as our ORM to perform database operations while mapping the data structures in our project.

### Apache Kafka

Will handle asynchronous **event-driven** communication using the pub/sub architectural messaging pattern (e.g., triggering email notifications, processing payments, or sync tracking) ensuring the core monolith system remains responsive, non-blocking, and ready for a distributed event-driven microservices future.