/**
 * The Main class serves as the entry point for the Library Management System application.
 * It expects four command-line arguments representing the input and output file paths.
 *
 * Usage:
 * java Main items.txt users.txt commands.txt output.txt
 */
public class Main {

    /**
     * The main method initializes the system and processes input and output files.
     *
     * @param args Command-line arguments:
     *             args[0] - path to items.txt file
     *             args[1] - path to users.txt file
     *             args[2] - path to commands.txt file
     *             args[3] - path to output.txt file (for results)
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java Main <items.txt> <users.txt> <commands.txt> <output.txt>");
            return;
        }

        String itemsFile = args[0];
        String usersFile = args[1];
        String commandsFile = args[2];
        String outputFile = args[3];

        LibraryManagementSystem system = new LibraryManagementSystem();
        system.readItems(itemsFile);
        system.readUsers(usersFile);
        system.processCommands(commandsFile, outputFile);
    }
}