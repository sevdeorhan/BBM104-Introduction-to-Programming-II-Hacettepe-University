/**
 * Represents a visitor in the zoo who can only visit animals
 * and does not have permission to feed them.
 */
public class Visitor extends Person {

    /**
     * Constructs a Visitor object with the given name and ID.
     *
     * @param name The name of the visitor.
     * @param id   The ID of the visitor.
     */
    public Visitor(String name, String id) {
        super(name, id);
    }

    /**
     * Visitors are not allowed to feed animals.
     *
     * @param manager    The ZooManager (not used in this case).
     * @param animal     The animal the visitor attempted to feed.
     * @param mealCount  The number of meals (ignored).
     * @return A message indicating that visitors cannot feed animals.
     */
    @Override
    public String feedAnimal(ZooManager manager, Animal animal, int mealCount) {
        return getName() + " tried to feed " + animal.getName() + ".\n" +
                "Error: Visitors do not have the authority to feed animals.\n";
    }

    /**
     * Allows the visitor to visit an animal.
     *
     * @param animal The animal the visitor wants to visit.
     * @return A message confirming the visit.
     */
    @Override
    public String visitAnimal(Animal animal) {
        return getName() + " tried to register for a visit to " + animal.getName() + ".\n" +
                getName() + " successfully visited " + animal.getName() + ".";
    }
}
