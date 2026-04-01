/**
 * Represents a zoo personnel who can feed animals and clean habitats.
 */
public class Personnel extends Person {

    /**
     * Constructs a Personnel object with the given name and ID.
     *
     * @param name The name of the personnel.
     * @param id   The ID of the personnel.
     */
    public Personnel(String name, String id) {
        super(name, id);
    }

    /**
     * Feeds the given animal with the specified number of meals.
     * Checks if there is enough food before feeding.
     *
     * @param manager    The ZooManager to check and update food stocks.
     * @param animal     The animal to feed.
     * @param mealCount  The number of meals to feed.
     * @return A message describing the feeding result.
     */
    @Override
    public String feedAnimal(ZooManager manager, Animal animal, int mealCount) {
        String error = animal.handleFeeding(manager, mealCount);
        if (error != null) {
            return getName() + " attempts to feed " + animal.getName() + ".\n" + error + "\n";
        } else {
            return getName() + " attempts to feed " + animal.getName() + ".\n" + animal.feed(mealCount);
        }
    }

    /**
     * Cleans the habitat of the given animal.
     *
     * @param animal The animal whose habitat will be cleaned.
     * @return A message describing the cleaning action.
     */
    @Override
    public String visitAnimal(Animal animal) {
        return getName() + " attempts to clean " + animal.getName() + "'s habitat.\n" +
                getName() + " started cleaning " + animal.getName() + "'s habitat.\n" +
                animal.cleanHabitat();
    }
}
