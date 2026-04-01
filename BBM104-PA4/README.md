# 🛡️ 2D Tank Battle Game

A dynamic 2D desktop game developed with **JavaFX** for **Hacettepe University BBM104** (Introduction to Programming Laboratory II). The project focuses on real-time object interactions, physics, and graphical user interface (GUI) design.

## ✨ Key Features
* **Real-Time Combat:** Interactive tank controls including movement, rotation, and shooting mechanics.
* **Collision Engine:** Sophisticated detection between tanks, bullets, and different wall types (brick, steel, etc.).
* **Dynamic Animations:** Visual effects for bullet impacts and tank destructions using custom explosion sprites.
* **Smart UI:** Real-time tracking of player health, score, and remaining enemies directly on the game screen.
* **Level System:** Structured map loading with varied environmental obstacles.

## 🛠 Technical Stack
* **Language:** Java 8 (Oracle).
* **Framework:** JavaFX for GUI and 2D rendering.
* **OOP Concepts:**
    * **Game Loop:** Managed real-time updates and rendering cycles.
    * **Entity Management:** Base classes for game objects (Tanks, Bullets, Walls) with specialized behaviors.
    * **Resource Handling:** Efficient management of images, sounds, and map data.

## 🚀 Execution
To compile and run the game, ensure you have the JavaFX library configured:

```bash
javac Main.java
java Main map.txt
