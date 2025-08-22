
# Digital Time Capsule

## 📦 Project Overview
The Digital Time Capsule is a Java-based console application that allows users to create virtual time capsules containing messages. Each capsule is locked until a specified future date and can store text or image messages tagged with moods and categories. The application uses JDBC to persist data in a MySQL database and loads capsule data from the database on startup or user login.

---

## 🚀 Features
- User registration and login
- Create time capsules with unlock dates
- Add text and image messages to capsules
- Tag messages with moods and categories
- Unlock capsules when the unlock date arrives
- Search messages by mood or tag
- Persistent storage using MySQL and JDBC

---

## 🧠 Java Concepts Used
- **Object-Oriented Programming (OOP)**: Encapsulation, inheritance, polymorphism
- **Collections Framework**: `Map`, `List`, `ArrayList`, `HashMap`
- **Generics**: Type-safe collections
- **Interfaces and Implementations**: Service interfaces for capsules and messages
- **JDBC (Java Database Connectivity)**: Connecting and interacting with MySQL database
- **Enums**: Mood types defined using `enum`

