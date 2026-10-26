# 💊 Pharmacy Management System
A REST API for pharmacy stock management with JWT authentication,  role-based access control, automated expiry tracking using @Scheduled,  and comprehensive reporting features built with Spring Boot.

---

## ✨ Features
- 🔐 JWT Authentication (ADMIN / USER roles)
- 💊 Medicine CRUD with auto status tracking (ACTIVE / EXPIRING_SOON / EXPIRED / LOW_STOCK)
- 🏭 Supplier Management
- 📦 Stock Transaction tracking (IN / OUT)
- ⏰ Auto expiry check every day at 8AM (@Scheduled)
- 📊 Reports (expired value, category-wise stock, low stock list)

---

## 🛠️ Tech Stack
- Spring Boot 4.1.2 | Java 17
- Spring Security + JWT
- JPA & Hibernate | MySQL
- Lombok | Maven

---

## ⚙️ Setup
1. Clone the repo
2. Create MySQL database: `pharmacy_db`
3. Update `application.properties` with your DB credentials and JWT secret
4. Run: `mvn spring-boot:run`

---

## 🔑 Usage
```

POST /auth/register-admin → Register
POST /auth/login → Get JWT token
Add token to headers: Authorization: Bearer <token>

```

---

## 👨‍💻 Author
**Vijay Kumar** — [@vijaykumar-dev28](https://github.com/vijaykumar-dev28)
```
