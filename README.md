# 🥦 Vegetables Online Purchasing Website - Backend

## 📌 Overview
A complete backend application for an **online vegetable purchasing system**.  
It supports secure user authentication, role-based access, and online payments via Razorpay.  
Order types include: **DELIVERY**, **PICKUP**, and **SUBSCRIPTION**.

---

## 🚀 Features
- 🔐 **JWT Authentication** for secure login and API access
- 👥 **Role-based access control** (User vs Admin)
  - Admin has full access to manage users, orders, and payments
- 💳 **Razorpay Payment Gateway Integration**
  - Create orders, verify signatures, and process refunds
- 📦 Order management with enum-based classification (`OrderType`)
- RESTful APIs for user and order operations
- MySQL database integration
- Clean architecture ready for microservices

---

## 🛠️ Tech Stack
- **Java (Spring Boot)** – Backend framework
- **MySQL** – Database
- **Spring Security + JWT** – Authentication & authorization
- **Razorpay SDK** – Payment gateway
- **REST API** – Communication layer
- **Git & GitHub** – Version control
- **Docker

---

## 📂 Project Structure
- 'Entity'->Manages the database by using Jpa.And in that have multiple Entities.
- `Enum'->handles Enum related tasks.
- 'Filter'->handles security based topics.
- `Repository'->handles data fetching from database.
- `Service'->handles business logic of the software.
- 'config'->handles security and cors related topics.
- 'controller'->Handles Rest-Api's.
- 'dto'->Handles request and response topics.
- 'OrderingProjectApplication'->Entry point of the program.


## 📡 Example API Usage

### 🔐 Authentication (JWT)
- **Register User**  
  `POST /api/auth/register`
  {
    "name": "Abdul",
    "email": "abdul@example.com",
    "password": "123456"
    "phone number":"1234567891"
  }

POST /api/auth/login
  {
  "email": "abdul@example.com",
  "password": "123456"
}


POST /api/orders
{
  "orderType": "DELIVERY",
  "items": [
    { "id": 1, "quantity": 2 },
    { "id": 5, "quantity": 1 }
  ]

---

## ⚙️ Setup & Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Razak6431/Vegetables-online-purchasing-website-backend.git
