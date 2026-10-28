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
- Java 17
- Spring Boot 4.x
- Spring Security + JWT
- MySQL
- Lombok
- Maven

## 🚀 How to Run
1. Clone the repo
2. Set environment variables: DB_USERNAME, DB_PASSWORD, JWT_SECRET
3. Run: `mvn spring-boot:run`

## 🔐 Roles
| Role  | Access |
|-------|--------|
| ADMIN | Full access (medicines, suppliers, reports, stock) |
| USER  | View medicines, manage stock |

## 📌 API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /auth/register | Public | Register as USER |
| POST | /auth/login | Public | Login & get JWT token |
| POST | /auth/register-admin | ADMIN only | Register as ADMIN |

### Medicines
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /medicines | ADMIN | Add medicine |
| GET | /medicines | ADMIN, USER | Get all medicines |
| GET | /medicines/{id} | ADMIN, USER | Get by ID |
| PUT | /medicines/{id} | ADMIN | Update medicine |
| DELETE | /medicines/{id} | ADMIN | Delete medicine |
| GET | /medicines/expiring-soon | ADMIN, USER | Expiring medicines |
| GET | /medicines/expired | ADMIN, USER | Expired medicines |
| GET | /medicines/low-stock | ADMIN, USER | Low stock medicines |
| GET | /medicines/search?name= | ADMIN, USER | Search by name |
| GET | /medicines/category?category= | ADMIN, USER | Filter by category |

### Suppliers
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /suppliers | ADMIN | Add supplier |
| GET | /suppliers | ADMIN | Get all suppliers |
| PUT | /suppliers/{id} | ADMIN | Update supplier |
| DELETE | /suppliers/{id} | ADMIN | Delete supplier |

### Stock
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /stock | ADMIN, USER | Add stock transaction |
| GET | /stock | ADMIN, USER | Get all transactions |

### Reports
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | /reports | ADMIN | Get pharmacy report |
