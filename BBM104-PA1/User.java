import java.util.ArrayList;
import java.util.List;

/**
 * Represents a general user in the library system.
 * Stores basic user information such as ID, name, phone number,
 * penalties, borrowing limits, and borrowed items.
 * This class is intended to be extended by more specific user types
 * like Student, AcademicStaff, and Guest.
 */
public class User {
    private String id;
    private String name;
    private String phoneNumber;
    private int penalty = 0;
    private int maxItems;
    private List<LibraryItem> borrowedItems = new ArrayList<>();
    private int overdueLimit;

    /**
     * Constructs a User with specified details and limits.
     *
     * @param id           the ID of the user
     * @param name         the full name of the user
     * @param phoneNumber  user's phone number
     * @param maxItems     max number of items user can borrow
     * @param overdueLimit number of days after which a penalty is applied
     */
    public User(String id, String name, String phoneNumber, int maxItems, int overdueLimit) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.maxItems = maxItems;
        this.overdueLimit = overdueLimit;
    }

    // --- Getters ---

    /**
     * @return the user ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return the full name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @return the phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return the current penalty amount of the user
     */
    public int getPenalty() {
        return penalty;
    }

    /**
     * @return the maximum number of items the user can borrow
     */
    public int getMaxItems() {
        return maxItems;
    }

    /**
     * @return the overdue limit in days before a penalty is applied
     */
    public int getOverdueLimit() {
        return overdueLimit;
    }

    /**
     * @return list of items currently borrowed by the user
     */
    public List<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }

    /**
     * Returns the basic user information (name and phone).
     *
     * @return formatted user information
     */
    public String getBaseInfo() {
        return "Name: " + name + " Phone: " + phoneNumber;
    }

    // --- Penalty actions ---

    /**
     * Adds the specified amount to the user's penalty.
     *
     * @param penalty the amount to be added to the penalty
     */
    public void addPenalty(int penalty) {
        this.penalty += penalty;
    }

    /**
     * Resets the user's penalty to zero.
     */
    public void resetPenalty() {
        this.penalty = 0;
    }

    // --- Borrow actions ---

    /**
     * Adds an item to the user's borrowed list.
     *
     * @param item the item to borrow
     */
    public void borrowItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    /**
     * Removes an item from the user's borrowed list.
     *
     * @param item the item to return
     */
    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
    }

    /**
     * Determines if a user can borrow a specific item type.
     * Subclasses can override this to enforce specific rules.
     *
     * @param itemType type of the item (e.g., normal, rare)
     * @return true if the user is allowed to borrow the item
     */
    public boolean canBorrowItem(String itemType) {
        return true;
    }

    /**
     * Returns formatted user information including penalties (if any).
     *
     * @return user information for display
     */
    public String getUserInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(getBaseInfo());
        if (penalty > 0) {
            sb.append("\nPenalty: ").append(penalty).append("$");
        }
        sb.append("\n");
        return sb.toString();
    }
}