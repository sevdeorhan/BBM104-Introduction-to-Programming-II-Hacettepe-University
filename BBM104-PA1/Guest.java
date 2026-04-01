/**
 * Represents a guest user in the library system.
 * Guest users are limited in the types of items they can borrow and have a short borrowing period.
 */
public class Guest extends User {
    private String occupation;

    /**
     * Constructs a Guest user.
     *
     * @param id          the ID of the guest
     * @param name        the name of the guest
     * @param phoneNumber the phone number of the guest
     * @param occupation  the occupation of the guest
     * @param maxItems    maximum number of items the guest can borrow
     */
    public Guest(String id, String name, String phoneNumber, String occupation, int maxItems) {
        super(id, name, phoneNumber, 1, 7); // maxItems: 1, overdueLimit: 7
        this.occupation = occupation;
    }

    /**
     * @return the occupation of the guest
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Guests are not allowed to borrow rare or limited items.
     *
     * @param itemType the type of the item
     * @return true if the guest can borrow the item, false otherwise
     */
    @Override
    public boolean canBorrowItem(String itemType) {
        return !itemType.equalsIgnoreCase("rare") && !itemType.equalsIgnoreCase("limited");
    }

    /**
     * Returns formatted guest information for display.
     *
     * @return guest details as a string
     */
    @Override
    public String getBaseInfo() {
        return "Name: " + getName() + " Phone: " + getPhoneNumber() + "\n" +
                "Occupation: " + getOccupation();
    }
}