/**
 * Represents a Lion in the zoo.
 * A lion eats only meat and has specific feeding and cleaning behavior.
 */
public class Lion extends Animal {

    /**
     * Constructs a Lion with the given name and age.
     *
     * @param name The lion's name.
     * @param age  The lion's age.
     */
    public Lion(String name, int age) {
        super(name, age);
    }

    /**
     * Feeds the lion with a calculated amount of meat.
     *
     * @param mealCount Number of meals to feed.
     * @return Description of the feeding result.
     */
    @Override
    public String feed(int mealCount) {
        double oneMeal = 5.0 + (getAge() - 5) * 0.05;
        double totalMeal = round(oneMeal * mealCount);
        return getName() + " has been given " + String.format("%.3f", round(totalMeal)).replace(",", ".") + " kgs of meat.\n";
    }

    /**
     * Cleans the lion's habitat.
     *
     * @return Description of the cleaning action.
     */
    @Override
    public String cleanHabitat() {
        return "Cleaning " + getName() + "'s habitat: Removing bones and refreshing sand.";
    }

    /**
     * Handles the food usage during feeding.
     *
     * @param manager   The ZooManager managing food stock.
     * @param mealCount Number of meals.
     * @return null if food is enough, otherwise an error message.
     */
    public String handleFeeding(ZooManager manager, int mealCount) {
        double oneMeal = 5.0 + (getAge() - 5) * 0.05;
        double totalMeal = round(oneMeal * mealCount);

        if (manager.useFood("meat", totalMeal)) {
            return null;
        } else {
            return "Error: Not enough meat.";
        }
    }

    /**
     * Checks if feeding is possible with current meat stock.
     *
     * @param manager   The ZooManager.
     * @param mealCount Number of meals.
     * @return true if meat is enough, false otherwise.
     */
    public boolean useFoodStock(ZooManager manager, int mealCount) {
        return handleFeeding(manager, mealCount) == null;
    }

    /**
     * Rounds a double to 3 decimal places.
     *
     * @param value The value to round.
     * @return Rounded value.
     */
    private static double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
