# 📚 University Library Management System

An Object-Oriented Programming (OOP) project developed for **Hacettepe University BBM104** (Introduction to Programming Laboratory II). The system simulates a comprehensive library environment with different user roles and item-specific borrowing logic.

## ✨ Key Features
* **Multi-User Role Management:** Distinct borrowing rules and limits for **Students**, **Academic Staff**, and **Guests**.
* **Diverse Library Items:** Specialized handling and attributes for **Books**, **Magazines**, and **DVDs**.
* **Automated Rule Engine:**
    * Tracks borrowing limits (Max items per user).
    * Manages overdue dates and applies a $2 penalty for late returns.
    * Enforces item-type restrictions (e.g., Students cannot borrow Reference items).
* **Data Processing:** Handles bulk operations by reading from `item.txt`, `users.txt`, and `commands.txt`.

## 🛠 Technical Stack
* **Language:** Java 8 (Oracle).
* **OOP Principles:**
    * **Inheritance:** Implemented base classes for Users and Library Items to enhance reusability.
    * **Encapsulation:** Protecting data through private attributes and modular design.
    * **Polymorphism:** Method overriding for specialized item information and borrowing logic.

## 🚀 Execution
To compile and run the system, use the following commands in the terminal:

```bash
javac Main.java
java Main items.txt users.txt commands.txt output.txt
