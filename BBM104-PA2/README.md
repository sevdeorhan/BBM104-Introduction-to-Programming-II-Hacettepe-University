# 🦁 Zoo Manager System

An advanced Object-Oriented Programming (OOP) project developed for **Hacettepe University BBM104** (Introduction to Programming Laboratory II). This system simulates the daily operations of a zoo, focusing on animal care, personnel management, and food stock tracking.

## ✨ Key Features
* [cite_start]**Dynamic Animal Management:** Implements specific behaviors and nutritional needs for **Lions**, **Elephants**, **Penguins**, and **Chimpanzees**[cite: 200, 202, 208, 210].
* [cite_start]**Role-Based Authority:** * **Personnel:** Authorized to feed animals and clean their habitats[cite: 219].
    * [cite_start]**Visitors:** Can visit animals but are restricted from feeding or cleaning operations[cite: 222, 223].
* [cite_start]**Complex Nutritional Logic:** Calculates daily meal sizes dynamically based on the animal's species and age[cite: 196, 201, 203].
* [cite_start]**Stock Tracking:** Real-time monitoring of meat, plant, and fish stocks with automated depletion during feeding[cite: 241, 292].
* [cite_start]**Robust Error Handling:** Utilizes custom exceptions to manage unauthorized actions, non-existent entities, and insufficient food stock[cite: 191, 199, 216].

## 🛠 Technical Stack
* [cite_start]**Language:** Java 8 (Oracle)[cite: 187].
* **OOP Principles:**
    * [cite_start]**Abstraction:** Used abstract base classes for `Animal` and `Person` to define core structures[cite: 190].
    * [cite_start]**Polymorphism:** Leveraged method overriding to execute species-specific feeding and cleaning logic[cite: 190, 198].
    * [cite_start]**Exception Handling:** Implemented a resilient system that catches errors without interrupting the program's runtime[cite: 227].

## 🚀 Execution
To compile and run the system with the required initialization files:

```bash
javac Main.java
java Main animals.txt persons.txt foods.txt commands.txt
