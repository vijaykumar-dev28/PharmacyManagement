# 💊 Pharmacy Management System — Backend (Spring Boot REST API)

A secure, production-structured REST API for pharmacy stock management. Built with **Java 17**, **Spring Boot 4.x**, **Spring Security**, **JWT Authentication**, **MySQL**, and featuring automated expiry tracking, stock transaction history, supplier management, and comprehensive reporting.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1.2 |
| Security | Spring Security + JWT (jjwt 0.12.6) |
| Database | MySQL |
| ORM | Spring Data JPA / Hibernate |
| Validation | Jakarta Bean Validation |
| Boilerplate Reduction | Lombok |
| API Documentation | Swagger UI (springdoc-openapi 2.8.9) |
| Build Tool | Maven |

---

## ✨ Features

- 🔐 **JWT-based Authentication** — Stateless login with signed JWT tokens (24-hour expiry)
- 👤 **Role-based Access Control** — ADMIN and USER roles with fine-grained endpoint protection
- 💊 **Medicine CRUD** — Add, view, update, and delete medicines with full validation
- 🏷️ **Auto Status Tracking** — Medicine status auto-calculated on every add/update/stock change
- 🏭 **Supplier Management** — Full CRUD for suppliers linked to medicines
- 📦 **Stock Transaction Tracking** — Record stock IN (purchase) and OUT (sale) with reasons
- ⏰ **Automated Expiry Scheduler** — Cron job runs every day at 8:00 AM to auto-update medicine statuses
- 📊 **Report Generation** — Expired stock value, expiring-soon value, category-wise stock, low stock list
- 🔍 **Search & Filter** — Search by name, filter by category, filter by status
- ✅ **Input Validation** — All request DTOs validated with `@NotBlank`, `@NotNull`, `@Min`, `@Email`
- ⚠️ **Global Exception Handling** — Centralized error responses via `@ControllerAdvice`
- 🌐 **CORS Configured** — Allows cross-origin requests from configured frontend origin
- 🌱 **Auto Admin Seeding** — Default admin account created on first startup via environment variable
- 📄 **Swagger UI** — Interactive API documentation available at `/swagger-ui.html`

---

## 🏷️ Medicine Status Logic

Status is automatically determined on every add, update, and stock change — and refreshed daily by the scheduler:

| Status | Condition |
|---|---|
| `ACTIVE` | Good stock, not expiring soon |
| `EXPIRING_SOON` | Expiry date within the next 30 days |
| `EXPIRED` | Expiry date has already passed |
| `LOW_STOCK` | Stock quantity below 10 units |

---

## 📁 Project Structure

```
src/main/java/com/pharmacy/
│
├── controller/
│   ├── AuthController.java                # Register & Login
│   ├── MedicineController.java            # Medicine CRUD + search + filters
│   ├── SupplierController.java            # Supplier CRUD
│   ├── StockTransactionController.java    # Stock IN / OUT + history
│   └── ReportController.java             # Pharmacy reports
│
├── service/
│   ├── AuthService.java                   # Auth logic (register, login, JWT)
│   ├── MedicineService.java               # Medicine business logic + status logic
│   ├── SupplierService.java               # Supplier business logic
│   ├── StockTransactionService.java       # Stock IN/OUT logic + quantity updates
│   └── ReportService.java                # Report aggregation logic
│
├── repository/
│   ├── MedicineRepository.java            # JPA queries for medicines
│   ├── SupplierRepository.java            # JPA queries for suppliers
│   ├── StockTransactionRepository.java    # JPA queries for stock transactions
│   └── UserRepository.java               # JPA queries for users
│
├── entity/
│   ├── Medicine.java                      # Medicine DB entity
│   ├── Supplier.java                      # Supplier DB entity
│   ├── StockTransaction.java              # Stock transaction DB entity
│   └── User.java                          # User (ADMIN/USER) DB entity
│
├── dto/
│   ├── request/
│   │   ├── AuthRequestDTO.java
│   │   ├── MedicineRequestDTO.java
│   │   ├── SupplierRequestDTO.java
│   │   └── StockTransactionRequestDTO.java
│   └── response/
│       ├── AuthResponseDTO.java
│       ├── MedicineResponseDTO.java
│       ├── SupplierResponseDTO.java
│       └── StockTransactionResponseDTO.java
│
├── security/
│   ├── SecurityConfig.java               # Spring Security filter chain + CORS
│   ├── JwtUtil.java                      # JWT generation & validation
│   ├── JwtFilter.java                    # JWT request filter (OncePerRequestFilter)
│   └── CustomUserDetailsService.java     # UserDetailsService implementation
│
├── enums/
│   ├── Role.java                          # ADMIN, USER
│   ├── Category.java                      # ANTIBIOTIC, PAINKILLER, VITAMIN, ANTACID...
│   ├── MedicineStatus.java               # ACTIVE, EXPIRING_SOON, EXPIRED, LOW_STOCK
│   └── TransactionType.java              # IN, OUT
│
├── exceptions/
│   ├── GlobalExceptionHandler.java
│   ├── MedicineNotFoundException.java
│   ├── SupplierNotFoundException.java
│   ├── UserNotFoundException.java
│   ├── DuplicateMedicineException.java
│   ├── DuplicateSupplierException.java
│   ├── DuplicateUserException.java
│   └── InsufficientStockException.java
│
├── scheduler/
│   └── ExpiryScheduler.java              # Cron job — runs daily at 8:00 AM
│
└── DataInitializer.java                  # Seeds default admin on startup
```

---

## 🔌 API Endpoints

**Base URL:** `http://localhost:8080`

All endpoints except `/auth/register` and `/auth/login` require a valid JWT in the `Authorization: Bearer <token>` header.

---

### 🔐 Auth

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/auth/register` | Public | Register as a USER |
| POST | `/auth/login` | Public | Login and receive a JWT token |
| POST | `/auth/register-admin` | ADMIN only | Register a new ADMIN user |

**Login / Register — Request Body:**
```json
{
  "username": "your_username",
  "password": "your_password"
}
```

**Login — Response:**
```json
{
  "username": "your_username",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "ADMIN"
}
```

---

### 💊 Medicines

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/medicines` | ADMIN | Add a new medicine |
| GET | `/medicines` | ADMIN, USER | Get all medicines |
| GET | `/medicines/{id}` | ADMIN, USER | Get medicine by ID |
| PUT | `/medicines/{id}` | ADMIN | Update medicine by ID |
| DELETE | `/medicines/{id}` | ADMIN | Delete medicine by ID |
| GET | `/medicines/expiring-soon` | ADMIN, USER | Get medicines expiring within 30 days |
| GET | `/medicines/expired` | ADMIN, USER | Get all expired medicines |
| GET | `/medicines/low-stock` | ADMIN, USER | Get medicines with stock below 10 |
| GET | `/medicines/search?name=` | ADMIN, USER | Search medicines by name (case-insensitive) |
| GET | `/medicines/category?category=` | ADMIN, USER | Filter medicines by category |

**Medicine — Request Body:**
```json
{
  "name": "Paracetamol",
  "category": "PAINKILLER",
  "manufacturer": "Sun Pharma",
  "price": 25.50,
  "expiryDate": "2026-12-31",
  "supplierId": 1
}
```

**Available category values:**
`ANTIBIOTIC` · `PAINKILLER` · `VITAMIN` · `ANTACID` · `ANTIFUNGAL` · `ANTIVIRAL` · `DIABETES` · `BLOOD_PRESSURE` · `OTHER`

---

### 🏭 Suppliers

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/suppliers` | ADMIN | Add a new supplier |
| GET | `/suppliers` | ADMIN | Get all suppliers |
| GET | `/suppliers/{id}` | ADMIN | Get supplier by ID |
| PUT | `/suppliers/{id}` | ADMIN | Update supplier by ID |
| DELETE | `/suppliers/{id}` | ADMIN | Delete supplier by ID |

**Supplier — Request Body:**
```json
{
  "name": "MedPlus Distributors",
  "contactNumber": "9876543210",
  "email": "medplus@example.com"
}
```

---

### 📦 Stock Transactions

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/stock/add` | ADMIN, USER | Add stock (purchase) — TransactionType: IN |
| POST | `/stock/sell` | ADMIN, USER | Sell stock — TransactionType: OUT |
| GET | `/stock/transactions` | ADMIN, USER | Get all stock transactions |
| GET | `/stock/transactions/{medicineId}` | ADMIN, USER | Get transactions for a specific medicine |

**Stock — Request Body:**
```json
{
  "medicineId": 1,
  "quantity": 50,
  "reason": "Monthly restock from supplier"
}
```

> ⚠️ Selling more than available stock throws `InsufficientStockException` (400 Bad Request).
> Medicine status is automatically recalculated after every stock change.

---

### 📊 Reports

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/reports/expired-value` | ADMIN | Total monetary value of all expired stock |
| GET | `/reports/expiring-soon` | ADMIN | Total monetary value of expiring-soon stock |
| GET | `/reports/category-wise` | ADMIN | Stock quantity grouped by medicine category |
| GET | `/reports/low-stock-list` | ADMIN | Full list of low stock medicines |

**Category-wise Stock — Sample Response:**
```json
{
  "PAINKILLER": 120,
  "ANTIBIOTIC": 45,
  "VITAMIN": 200,
  "ANTACID": 30
}
```

---

## ⏰ Automated Expiry Scheduler

A background cron job runs every day at **8:00 AM** and scans all medicines. If a medicine's status has changed (e.g., it just expired or dropped to low stock), it automatically updates the status in the database.

```
Cron expression: 0 0 8 * * *  →  Every day at 08:00 AM
```

This ensures status accuracy even without any user interaction.

---

## ⚙️ Setup & Running Locally

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

### 1. Clone the Repository
```bash
git clone https://github.com/vijaykumar-dev28/PharmacyManagement.git
cd PharmacyManagement
```

### 2. Create the Database
```sql
CREATE DATABASE pharmacy_db;
```
The app auto-creates all tables via `spring.jpa.hibernate.ddl-auto=update` — no SQL scripts needed.

### 3. Configure Environment Variables

This project uses environment variables to keep all sensitive values out of the codebase. Refer to `.env.example` for the full list:

```env
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_jwt_secret_key_minimum_32_characters_long
ADMIN_DEFAULT_PASSWORD=choose_a_strong_admin_password
```

**Setting variables — Windows (Command Prompt):**
```cmd
set DB_USERNAME=root
set DB_PASSWORD=yourpassword
set JWT_SECRET=mysupersecretkey1234567890123456
set ADMIN_DEFAULT_PASSWORD=yourAdminPassword
```

**Setting variables — macOS / Linux:**
```bash
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
export JWT_SECRET=mysupersecretkey1234567890123456
export ADMIN_DEFAULT_PASSWORD=yourAdminPassword
```

### 4. Run the Application
```bash
./mvnw spring-boot:run
```
The server starts at: `http://localhost:8080`

### 5. Default Admin Account
On first startup, a default admin is automatically created using the `ADMIN_DEFAULT_PASSWORD` environment variable. Use username `admin` with that password to log in via `/auth/login`.

---

## 📄 Swagger UI

Interactive API documentation is available once the app is running:

```
http://localhost:8080/swagger-ui.html
```

All endpoints, request bodies, and response schemas are documented and testable directly from the browser.

---

## 🧪 Testing with Postman

1. **Register** — `POST /auth/register` with your credentials (gets USER role)
2. **Login** — `POST /auth/login` and copy the `token` from the response
3. For all protected requests — go to the **Authorization** tab → select **Bearer Token** → paste the token
4. ADMIN-only endpoints require logging in with the default admin account

---

## 🔒 Security Design

| Concern | Approach |
|---|---|
| Password storage | BCrypt hashing |
| Authentication | Stateless JWT (no sessions) |
| Token expiry | 24 hours (86400000 ms) |
| Secret management | Environment variables only — never hardcoded |
| CORS | Restricted to configured frontend origin (`http://localhost:5173`) |
| Role enforcement | Fine-grained per HTTP method and endpoint |
| Swagger access | Permitted without auth for development convenience |

---

## 🔗 Related Repository

**Frontend (React + Vite):** [PharmacyManagement-Frontend](https://github.com/vijaykumar-dev28/PharmacyManagement-Frontend)
---

## 👨‍💻 Author

**Vijaykumar** — Java Full Stack Developer
[GitHub](https://github.com/vijaykumar-dev28) · [LinkedIn](https://www.linkedin.com/in/vijaykumar-dev28)
