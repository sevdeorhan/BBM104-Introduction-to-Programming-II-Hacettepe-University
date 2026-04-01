/**
 * The entry point of the Zoo Management program.
 * <p>
 * This class loads data from input files and processes commands.
 * It uses the ZooManager class to manage animals, people, food stock, and commands.
 */
public class Main {

    /**
     * The main method of the program. It expects exactly four command-line arguments:
     * the paths to animals.txt, persons.txt, foods.txt, and commands.txt.
     * It processes the commands and writes the output to output.txt.
     *
     * @param args Command-line arguments: animalsFile, personsFile, foodsFile, commandsFile
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java Main <animals.txt> <persons.txt> <foods.txt> <commands.txt>");
            return;
        }

        String animalsFile = args[0];
        String personsFile = args[1];
        String foodsFile = args[2];
        String commandsFile = args[3];
        String outputFile = "output.txt";

        ZooManager manager = new ZooManager();

        try {
            manager.readAnimals(animalsFile);
            manager.readPersons(personsFile);
            manager.readFoods(foodsFile);
            manager.processCommands(commandsFile, outputFile);
            System.out.println("Commands processed. Output written to: " + outputFile);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
