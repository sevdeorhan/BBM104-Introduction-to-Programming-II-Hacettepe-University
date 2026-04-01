/**
 * Represents a Penguin in the zoo.
 * Penguins eat fish and have an icy habitat.
 */
public class Penguin extends Animal {

    /**
     * Constructs a Penguin with the specified name and age.
     *
     * @param name The penguin's name.
     * @param age  The penguin's age.
     */
    public Penguin(String name, int age) {
        super(name, age);
    }

    /**
     * Calculates and returns a feeding message for the penguin.
     * Penguins eat fish, and the amount increases slightly with age.
     *
     * @param mealCount Number of meals.
     * @return Feeding message.
     */
    @Override
    public String feed(int mealCount) {
        double oneMeal = 3.0 + (getAge() - 4) * 0.04;
        double totalMeal = round(mealCount * oneMeal);

        return getName() + " has been given " +
                String.format("%.3f", round(totalMeal)).replace(",", ".") + " kgs of fish.\n";
    }

    /**
     * Returns a description of how the penguin's habitat is cleaned.
     *
     * @return Cleaning message.
     */
    @Override
    public String cleanHabitat() {
        return "Cleaning " + getName() + "'s habitat: Replenishing ice and scrubbing walls.";
    }

    /**
     * Tries to feed the penguin by checking the food stock.
     * Uses the food if enough is available.
     *
     * @param manager   The ZooManager that manages the food stock.
     * @param mealCount Number of meals.
     * @return null if successful; otherwise an error message.
     */
    @Override
    public String handleFeeding(ZooManager manager, int mealCount) {
        double oneMeal = 3.0 + (getAge() - 4) * 0.04;
        double totalMeal = round(oneMeal * mealCount);

        if (manager.hasEnoughFood("fish", totalMeal)) {
            manager.useFood("fish", totalMeal);
            return null;
        } else {
            return "Error: Not enough fish.";
        }
    }

    /**
     * Checks if enough food is available to feed the penguin.
     *
     * @param manager   The ZooManager.
     * @param mealCount Number of meals.
     * @return true if feeding is possible; false otherwise.
     */
    public boolean useFoodStock(ZooManager manager, int mealCount) {
        return handleFeeding(manager, mealCount) == null;
    }

    /**
     * Rounds a double to three decimal places.
     *
     * @param value The value to round.
     * @return Rounded value.
     */
    private static double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
