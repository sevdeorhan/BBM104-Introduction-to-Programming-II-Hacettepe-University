import java.time.LocalDate;

/**
 * Represents a general item in the library such as a book, magazine, or DVD.
 * This is the base class for all specific item types.
 */
public class LibraryItem {
    private String id;
    private String name;
    private String type; // normal, referenced, rare, limited
    private boolean isAvailable = true; // Default is available
    private LocalDate borrowDate;
    private User borrowedBy;

    /**
     * Constructs a LibraryItem with given ID, name, and type.
     *
     * @param id    The unique ID of the item
     * @param name  The name/title of the item
     * @param type  The item type (e.g., normal, referenced, rare, limited)
     */
    public LibraryItem(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    /**
     * @return The ID of the item
     */
    public String getId() {
        return id;
    }

    /**
     * @return The name/title of the item
     */
    public String getName() {
        return name;
    }

    /**
     * @return The type of the item
     */
    public String getType() {
        return type;
    }

    /**
     * @return True if the item is available to borrow, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * @return The date when the item was borrowed
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * @return The user who borrowed the item
     */
    public User getBorrowedBy() {
        return borrowedBy;
    }

    /**
     * Sets whether the item is available for borrowing.
     *
     * @param available True if the item is available, false if borrowed
     */
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    /**
     * Sets the borrow date of the item.
     *
     * @param borrowDate The date the item was borrowed
     */
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * Sets the user who borrowed this item.
     *
     * @param user The user who borrowed the item
     */
    public void setBorrowedBy(User user) {
        this.borrowedBy = user;
    }

    /**
     * Returns a string with extra information specific to the item.
     * This method should be overridden by subclasses like Book, Magazine, or DVD.
     *
     * @return A string with item-specific information
     */
    public String getItemInfo() {
        return "";
    }
}