/**
 * Abstract class representing a person in the zoo.
 * Can be either a Visitor or a Personnel.
 */
abstract class Person {
    private String name;
    private String id;

    /**
     * Constructs a person with the given name and ID.
     *
     * @param name The name of the person.
     * @param id   The unique ID of the person.
     */
    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the person's name.
     *
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the person's ID.
     *
     * @return The ID of the person.
     */
    public String getId() {
        return id;
    }

    /**
     * Abstract method to handle feeding an animal.
     * Implemented differently by Visitor and Personnel.
     *
     * @param manager    The ZooManager that handles food stocks.
     * @param animal     The animal to feed.
     * @param mealCount  The number of meals.
     * @return A message describing the result of the feeding attempt.
     */
    public abstract String feedAnimal(ZooManager manager, Animal animal, int mealCount);

    /**
     * Abstract method to handle visiting or cleaning an animal's habitat.
     *
     * @param animal The animal to visit or clean.
     * @return A message describing the result of the visit or cleaning.
     */
    public abstract String visitAnimal(Animal animal);
}
