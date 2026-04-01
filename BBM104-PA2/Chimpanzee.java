/**
 * Represents a Chimpanzee in the zoo.
 * Chimpanzees eat both meat and plant-based food (leaves).
 */
public class Chimpanzee extends Animal {

    /**
     * Constructs a Chimpanzee with the given name and age.
     *
     * @param name The chimpanzee's name.
     * @param age  The chimpanzee's age.
     */
    public Chimpanzee(String name, int age) {
        super(name, age);
    }

    /**
     * Feeds the chimpanzee with equal parts of meat and plant-based food.
     *
     * @param mealCount Number of meals.
     * @return Feeding result message.
     */
    @Override
    public String feed(int mealCount) {
        double oneMeal = 6.0 + (getAge() - 10) * 0.025;
        double totalMeal = round(mealCount * oneMeal);
        double meat = round(totalMeal / 2);
        double leaves = round(totalMeal / 2);

        return getName() + " has been given " +
                String.format("%.3f", round(meat)).replace(",", ".") + " kgs of meat and " +
                String.format("%.3f", round(leaves)).replace(",", ".") + " kgs of leaves.\n";
    }

    /**
     * Cleans the chimpanzee's habitat.
     *
     * @return Cleaning description.
     */
    @Override
    public String cleanHabitat() {
        return "Cleaning " + getName() + "'s habitat: Sweeping the enclosure and replacing branches.";
    }

    /**
     * Checks and uses required meat and plant food for feeding.
     * Returns an error message if food is not sufficient.
     *
     * @param manager   The ZooManager managing food stock.
     * @param mealCount Number of meals.
     * @return null if feeding is successful; otherwise an error message.
     */
    @Override
    public String handleFeeding(ZooManager manager, int mealCount) {
        double total = round((6.0 + (getAge() - 10) * 0.025) * mealCount);
        double meat = round(total / 2);
        double plant = round(total / 2);

        boolean enoughMeat = manager.hasEnoughFood("meat", meat);
        boolean enoughPlant = manager.hasEnoughFood("plant", plant);

        if (!enoughMeat && !enoughPlant) {
            return "Error: Not enough Meat and Plant.";
        } else if (!enoughMeat) {
            return "Error: Not enough meat.";
        } else if (!enoughPlant) {
            return "Error: Not enough plant.";
        }

        manager.useFood("meat", meat);
        manager.useFood("plant", plant);
        return null;
    }

    /**
     * Checks if the stock is enough for feeding.
     *
     * @param manager   The ZooManager.
     * @param mealCount Number of meals.
     * @return true if feeding is possible; otherwise false.
     */
    public boolean useFoodStock(ZooManager manager, int mealCount) {
        return handleFeeding(manager, mealCount) == null;
    }

    /**
     * Rounds a number to three decimal places.
     *
     * @param value The value to round.
     * @return Rounded value.
     */
    private static double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
