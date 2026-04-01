/**
 * Represents an Elephant in the zoo.
 * Elephants eat plant-based food and have specific habitat cleaning needs.
 */
public class Elephant extends Animal {

    /**
     * Constructs an Elephant with the given name and age.
     *
     * @param name The elephant's name.
     * @param age  The elephant's age.
     */
    public Elephant(String name, int age) {
        super(name, age);
    }

    /**
     * Feeds the elephant with a calculated amount of plant-based food.
     *
     * @param mealCount Number of meals to feed.
     * @return Description of the feeding result.
     */
    @Override
    public String feed(int mealCount){
        double oneMeal = 10.0 + (getAge() - 20) * 0.015;
        double totalMeal = round(mealCount * oneMeal);
        return getName() + " has been given " + String.format("%.3f", round(totalMeal)).replace(",", ".") + " kgs assorted fruits and hay.\n";
    }

    /**
     * Cleans the elephant's habitat.
     *
     * @return Description of the cleaning action.
     */
    @Override
    public String cleanHabitat() {
        return "Cleaning " + getName() + "'s habitat: Washing the water area.";
    }

    /**
     * Handles the food usage during feeding.
     *
     * @param manager   The ZooManager managing food stock.
     * @param mealCount Number of meals.
     * @return null if food is enough, otherwise an error message.
     */
    @Override
    public String handleFeeding(ZooManager manager, int mealCount) {
        double oneMeal = 10.0 + (getAge() - 20) * 0.015;
        double totalMeal = round(oneMeal * mealCount);
        if (manager.hasEnoughFood("plant", totalMeal)) {
            manager.useFood("plant", totalMeal);
            return null;
        } else {
            return "Error: Not enough plant.";
        }
    }

    /**
     * Checks if feeding is possible with current plant stock.
     *
     * @param manager   The ZooManager.
     * @param mealCount Number of meals.
     * @return true if food is sufficient, false otherwise.
     */
    public boolean useFoodStock(ZooManager manager, int mealCount) {
        return handleFeeding(manager, mealCount) == null;
    }

    /**
     * Rounds a double value to 3 decimal places.
     *
     * @param value The value to round.
     * @return Rounded value.
     */
    private static double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
