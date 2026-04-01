/**
 * Represents a Magazine in the library.
 * This class extends LibraryItem and includes magazine-specific details such as publisher and category.
 */
public class Magazine extends LibraryItem {
    private String publisher;
    private String category;

    /**
     * Constructs a new Magazine object with the given information.
     *
     * @param id        The unique ID of the magazine
     * @param name      The name/title of the magazine
     * @param publisher The publisher of the magazine
     * @param category  The category of the magazine (e.g., Science, Fashion, etc.)
     * @param type      The type of the magazine (normal, limited, etc.)
     */
    public Magazine(String id, String name, String publisher, String category, String type) {
        super(id, name, type);
        this.publisher = publisher;
        this.category = category;
    }

    /**
     * @return The publisher of the magazine
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @return The category of the magazine
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns a string with the publisher and category of the magazine.
     * Overrides the method in LibraryItem.
     *
     * @return A formatted string with magazine-specific information
     */
    @Override
    public String getItemInfo() {
        return "Publisher: " + publisher + " Category: " + category;
    }
}