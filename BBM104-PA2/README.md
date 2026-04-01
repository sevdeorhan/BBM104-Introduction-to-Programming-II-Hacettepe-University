# 🦁 Zoo Manager System

An advanced Object-Oriented Programming (OOP) project developed for **Hacettepe University BBM104** (Introduction to Programming Laboratory II). This system simulates the daily operations of a zoo, focusing on animal care, personnel management, and food stock tracking.

## ✨ Key Features
* **Dynamic Animal Management:** Implements specific behaviors and nutritional needs for **Lions**, **Elephants**, **Penguins**, and **Chimpanzees**.
* **Role-Based Authority:** * **Personnel:** Authorized to feed animals and clean their habitats.
    * **Visitors:** Can visit animals but are restricted from feeding or cleaning operations.
* **Complex Nutritional Logic:** Calculates daily meal sizes dynamically based on the animal's species and age.
* **Stock Tracking:** Real-time monitoring of meat, plant, and fish stocks with automated depletion during feeding.
* **Robust Error Handling:** Utilizes custom exceptions to manage unauthorized actions, non-existent entities, and insufficient food stock.

## 🛠 Technical Stack
* **Language:** Java 8 (Oracle).
* **OOP Principles:**
    * **Abstraction:** Used abstract base classes for `Animal` and `Person` to define core structures.
    * **Polymorphism:** Leveraged method overriding to execute species-specific feeding and cleaning logic.
    * **Exception Handling:** Implemented a resilient system that catches errors without interrupting the program's runtime.

## 🚀 Execution
To compile and run the system with the required initialization files:

```bash
javac Main.java
java Main animals.txt persons.txt foods.txt commands.txt
