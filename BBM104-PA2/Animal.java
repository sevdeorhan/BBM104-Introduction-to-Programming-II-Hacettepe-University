import java.util.List;

/**
 * Abstract class representing a generic Animal in the zoo.
 * Provides a base structure for specific animal types.
 */
abstract class Animal {
    private String name;
    private int age;

    /**
     * Constructs a new Animal with a name and age.
     *
     * @param name The name of the animal.
     * @param age The age of the animal.
     */
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Gets the name of the animal.
     *
     * @return The animal's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the age of the animal.
     *
     * @return The animal's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Feeds the animal based on the number of meals.
     *
     * @param mealCount Number of meals to feed.
     * @return A string describing the feeding result.
     */
    public abstract String feed(int mealCount);

    /**
     * Cleans the animal's habitat.
     *
     * @return A string describing the cleaning action.
     */
    public abstract String cleanHabitat();

    /**
     * Handles the feeding logic by checking and using food stock.
     *
     * @param manager The ZooManager managing food stock.
     * @param mealCount Number of meals to attempt feeding.
     * @return null if successful, or an error message string if failed.
     */
    public abstract String handleFeeding(ZooManager manager, int mealCount);

    /**
     * Checks whether feeding is possible with current food stock.
     *
     * @param manager The ZooManager managing food stock.
     * @param mealCount Number of meals to check.
     * @return true if feeding is possible, false otherwise.
     */
    public abstract boolean useFoodStock(ZooManager manager, int mealCount);
}
