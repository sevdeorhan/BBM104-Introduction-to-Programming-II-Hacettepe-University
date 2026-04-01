/**
 * Represents a Book in the library.
 * This class extends LibraryItem and includes book-specific details such as author and genre.
 */
public class Book extends LibraryItem {
    private String author;
    private String genre;

    /**
     * Constructs a new Book object with the given information.
     *
     * @param id     The unique ID of the book
     * @param name   The name/title of the book
     * @param author The author of the book
     * @param genre  The genre of the book
     * @param type   The type of the book (e.g., normal, rare, etc.)
     */
    public Book(String id, String name, String author, String genre, String type) {
        super(id, name, type);
        this.author = author;
        this.genre = genre;
    }

    /**
     * @return The author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return The genre of the book
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Returns a string with the author and genre of the book.
     * Overrides the method in LibraryItem.
     *
     * @return A formatted string with book-specific information
     */
    @Override
    public String getItemInfo() {
        return "Author: " + author + " Genre: " + genre;
    }
}