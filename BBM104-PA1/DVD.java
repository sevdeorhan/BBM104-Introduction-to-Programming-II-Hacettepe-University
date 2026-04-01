/**
 * Represents a DVD item in the library.
 * A DVD has a director, a category (e.g., Drama, Crime), and a runtime in minutes.
 * Inherits from {@link LibraryItem}.
 */
public class DVD extends LibraryItem {
    private String director;
    private String category;
    private int runtime;

    /**
     * Constructs a DVD with specified details.
     *
     * @param id        the unique ID of the DVD
     * @param name      the title of the DVD
     * @param director  the name of the director
     * @param category  the genre/category of the DVD
     * @param runtime   the duration of the DVD in minutes
     * @param type      the borrowing type (e.g., normal, rare)
     */
    public DVD(String id, String name, String director, String category, int runtime, String type) {
        super(id, name, type);
        this.director = director;
        this.category = category;
        this.runtime = runtime;
    }

    /**
     * @return the director of the DVD
     */
    public String getDirector() {
        return director;
    }

    /**
     * @return the category or genre of the DVD
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the runtime in minutes
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Returns a string with DVD-specific information.
     * Overrides {@link LibraryItem#getItemInfo()}.
     *
     * @return formatted DVD info (director, category, runtime)
     */
    @Override
    public String getItemInfo() {
        return "Director: " + director + " Category: " + category + " Runtime: " + runtime + " min";
    }
}