import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * This class manages the operations of a library system.
 * It handles reading item and user data from files, processing commands (borrow, return, pay),
 * and producing formatted output for users and items.
 */
public class LibraryManagementSystem {

    private List<User> users = new ArrayList<>();
    private List<LibraryItem> items = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Finds a user object by its ID.
     * @param id ID of the user
     * @return User object if found, otherwise null
     */
    private User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) return user;
        }
        return null;
    }

    /**
     * Finds a library item object by its ID.
     * @param id ID of the item
     * @return LibraryItem object if found, otherwise null
     */
    private LibraryItem findItemById(String id) {
        for (LibraryItem item : items) {
            if (item.getId().equals(id)) return item;
        }
        return null;
    }

    /**
     * Reads library items from a file and creates corresponding item objects (Book, Magazine, DVD).
     * @param fileName path to the items.txt file
     */
    public void readItems(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                String id = parts[1];
                String name = parts[2];

                if (type.equals("B")) {
                    String author = parts[3];
                    String genre = parts[4];
                    String itemType = parts[5];
                    Book book = new Book(id, name, author, genre, itemType);
                    items.add(book);
                } else if (type.equals("M")) {
                    String publisher = parts[3];
                    String category = parts[4];
                    String itemType = parts[5];
                    Magazine magazine = new Magazine(id, name, publisher, category, itemType);
                    items.add(magazine);
                } else if (type.equals("D")) {
                    String director = parts[3];
                    String category = parts[4];
                    int runtime = Integer.parseInt(parts[5].replace("min", "").trim());
                    String itemType = parts[6];
                    DVD dvd = new DVD(id, name, director, category, runtime, itemType);
                    items.add(dvd);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads users from a file and creates corresponding user objects (Student, AcademicStaff, Guest).
     * @param fileName path to the users.txt file
     */
    public void readUsers(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                String phoneNumber = parts[3];

                if (type.equals("S")) {
                    String department = parts[4];
                    String faculty = parts[5];
                    int grade = Integer.parseInt(parts[6]);
                    Student student = new Student(name, id, phoneNumber, faculty, department, grade, 5);
                    users.add(student);
                } else if (type.equals("A")) {
                    String department = parts[4];
                    String faculty = parts[5];
                    String title = parts[6];
                    AcademicStaff academic = new AcademicStaff(name, id, phoneNumber, faculty, department, title, 3);
                    users.add(academic);
                } else if (type.equals("G")) {
                    String occupation = parts[4];
                    Guest guest = new Guest(name, id, phoneNumber, occupation, 1);
                    users.add(guest);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes commands from a file and writes results to the output file.
     * @param commandFile path to commands.txt
     * @param outputFile path to output.txt where results will be written
     */
    public void processCommands(String commandFile, String outputFile) {
        try (
                BufferedReader br = new BufferedReader(new FileReader(commandFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String command = parts[0];

                if (command.equals("borrow")) {
                    String userId = parts[1];
                    String itemId = parts[2];
                    LocalDate borrowDate = LocalDate.parse(parts[3], formatter);
                    handleBorrow(userId, itemId, borrowDate, writer);
                } else if (command.equals("return")) {
                    String userId = parts[1];
                    String itemId = parts[2];
                    handleReturn(userId, itemId, writer);
                } else if (command.equals("pay")) {
                    String userId = parts[1];
                    handlePay(userId, writer);
                } else if (command.equals("displayUsers")) {
                    writer.write("\n\n");
                    displayUsers(writer);
                } else if (command.equals("displayItems")) {
                    writer.write("\n");
                    displayItems(writer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the borrowing process, applies overdue checks, and updates item/user status.
     */
    private void handleBorrow(String userId, String itemId, LocalDate borrowDate, BufferedWriter writer) throws IOException {
        User user = findUserById(userId);
        LibraryItem item = findItemById(itemId);

        if (user == null || item == null) return;

        LocalDate today = LocalDate.now();

        // Return overdue items and apply penalties
        for (User u : users) {
            List<LibraryItem> borrowedItems = new ArrayList<>(u.getBorrowedItems());
            for (LibraryItem borrowedItem : borrowedItems) {
                LocalDate itemBorrowDate = borrowedItem.getBorrowDate();
                if (itemBorrowDate != null) {
                    long daysBetween = ChronoUnit.DAYS.between(itemBorrowDate, today);
                    if (daysBetween > u.getOverdueLimit()) {
                        u.returnItem(borrowedItem);
                        borrowedItem.setAvailable(true);
                        borrowedItem.setBorrowDate(null);
                        borrowedItem.setBorrowedBy(null);
                        u.addPenalty(2);
                    }
                }
            }
        }

        // Check borrowing conditions
        if (user.getPenalty() >= 6) {
            writer.write(user.getName() + " cannot borrow " + item.getName() + ", you must first pay the penalty amount! " + user.getPenalty() + "$\n");
            return;
        }
        if (user.getBorrowedItems().size() >= user.getMaxItems()) {
            writer.write(user.getName() + " cannot borrow " + item.getName() + ", since the borrow limit has been reached!\n");
            return;
        }
        if (!user.canBorrowItem(item.getType())) {
            writer.write(user.getName() + " cannot borrow " + item.getType() + " item!\n");
            return;
        }
        if (!item.isAvailable()) {
            writer.write(user.getName() + " cannot borrow " + item.getName() + ", it is not available!\n");
            return;
        }

        // Perform borrow
        item.setAvailable(false);
        item.setBorrowDate(borrowDate);
        item.setBorrowedBy(user);
        user.borrowItem(item);

        writer.write(user.getName() + " successfully borrowed! " + item.getName() + "\n");
    }

    /**
     * Handles the returning of an item by a user.
     */
    private void handleReturn(String userId, String itemId, BufferedWriter writer) throws IOException {
        User user = findUserById(userId);
        LibraryItem item = findItemById(itemId);

        if (user == null || item == null) return;

        if (user.getBorrowedItems().contains(item)) {
            user.returnItem(item);
            item.setAvailable(true);
            item.setBorrowDate(null);
            item.setBorrowedBy(null);
            writer.write(user.getName() + " successfully returned " + item.getName() + "\n");
        } else {
            writer.write(user.getName() + " cannot return " + item.getName() + ", it is not available!\n");
        }
    }

    /**
     * Handles penalty payment for a user.
     */
    private void handlePay(String userId, BufferedWriter writer) throws IOException {
        User user = findUserById(userId);

        if (user == null) return;

        if (user.getPenalty() >= 0) {
            user.resetPenalty();
            writer.write(user.getName() + " has paid penalty\n");
        }
    }

    /**
     * Writes sorted user information to the output file.
     */
    private void displayUsers(BufferedWriter writer) throws IOException {
        users.sort(Comparator.comparing(User::getId));
        for (User user : users) {
            writer.write("------ User Information for " + user.getId() + " ------\n");
            writer.write(user.getUserInfo() + "\n");
        }
    }

    /**
     * Writes sorted item information to the output file.
     */
    private void displayItems(BufferedWriter writer) throws IOException {
        items.sort(Comparator.comparing(LibraryItem::getId));
        int count = 0;
        for (LibraryItem item : items) {
            writer.write("------ Item Information for " + item.getId() + " ------\n");
            if (item.isAvailable()) {
                writer.write("ID: " + item.getId() + " Name: " + item.getName() + " Status: Available\n");
            } else {
                String borrower = item.getBorrowedBy().getName();
                String dateStr = item.getBorrowDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                writer.write("ID: " + item.getId() + " Name: " + item.getName() + " Status: Borrowed Borrowed Date: " + dateStr + " Borrowed by: " + borrower + "\n");
            }
            writer.write(item.getItemInfo());
            count++;
            if (count < items.size()) {
                writer.write("\n\n");
            } else {
                writer.write("\n");
            }
        }
    }
}