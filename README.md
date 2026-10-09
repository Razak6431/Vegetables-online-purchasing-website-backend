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
- `User` entity – Manages customer details
- `Role` entity – Defines access levels (Admin/User)
- `Order` entity – Handles order details
- `OrderType` enum – Classifies orders (DELIVERY, PICKUP, SUBSCRIPTION)
- `Payment` entity – Stores Razorpay transactions

---

## ⚙️ Setup & Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Razak6431/Vegetables-online-purchasing-website-backend.git
