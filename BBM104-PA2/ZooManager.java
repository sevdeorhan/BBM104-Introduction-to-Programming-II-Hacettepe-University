import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The ZooManager class handles the core operations of managing a zoo.
 * It includes functionality for reading data from files, maintaining
 * animal/person records, food stocks, and processing commands.
 */
public class ZooManager {

    private List<Animal> animals = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private double meatStock;
    private double fishStock;
    private double plantStock;

    /**
     * Reads animal data from a file and adds animals to the zoo.
     * @param fileName The name of the file containing animal data.
     */
    public void readAnimals(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                String typeAnimal = lines[0];
                String name = lines[1];
                int age = Integer.parseInt(lines[2]);

                if (typeAnimal.equalsIgnoreCase("Lion")) {
                    animals.add(new Lion(name, age));
                } else if (typeAnimal.equalsIgnoreCase("Elephant")) {
                    animals.add(new Elephant(name, age));
                } else if (typeAnimal.equalsIgnoreCase("Penguin")) {
                    animals.add(new Penguin(name, age));
                } else if (typeAnimal.equalsIgnoreCase("Chimpanzee")) {
                    animals.add(new Chimpanzee(name, age));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return List of all animals currently in the zoo.
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Reads visitor and personnel data from a file and stores them.
     * @param fileName The name of the file containing person data.
     */
    public void readPersons(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                String typePerson = lines[0];
                String name = lines[1];
                String id = lines[2];

                if (typePerson.equalsIgnoreCase("Visitor")) {
                    persons.add(new Visitor(name, id));
                } else if (typePerson.equalsIgnoreCase("Personnel")) {
                    persons.add(new Personnel(name, id));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return List of all persons (visitors and personnel) in the zoo.
     */
    public List<Person> getPersons() {
        return persons;
    }

    /**
     * Reads food stock data from a file and updates the inventory.
     * @param fileName The name of the file containing food stock data.
     */
    public void readFoods(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                String typeFood = lines[0].toLowerCase();
                double amount = Double.parseDouble(lines[1]);

                if (typeFood.equalsIgnoreCase("meat")) {
                    meatStock += amount;
                } else if (typeFood.equalsIgnoreCase("fish")) {
                    fishStock += amount;
                } else if (typeFood.equalsIgnoreCase("plant")) {
                    plantStock += amount;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the zoo has enough food of a specific type.
     * @param typeFood Type of food (meat, fish, or plant).
     * @param amount Amount required.
     * @return true if stock is sufficient, false otherwise.
     */
    public boolean hasEnoughFood(String typeFood, double amount) {
        typeFood = typeFood.toLowerCase();
        switch (typeFood) {
            case "meat": return meatStock >= amount;
            case "fish": return fishStock >= amount;
            case "plant": return plantStock >= amount;
            default: return false;
        }
    }

    /**
     * Uses a specific amount of food from the stock if available.
     * @param typeFood Type of food.
     * @param amount Amount to use.
     * @return true if usage was successful, false otherwise.
     */
    public boolean useFood(String typeFood, double amount) {
        typeFood = typeFood.toLowerCase();
        if (typeFood.equals("meat") && meatStock >= amount) {
            meatStock -= amount;
            return true;
        } else if (typeFood.equals("fish") && fishStock >= amount) {
            fishStock -= amount;
            return true;
        } else if (typeFood.equals("plant") && plantStock >= amount) {
            plantStock -= amount;
            return true;
        }
        return false;
    }

    /**
     * Displays the current food stock to the console.
     */
    public void foodStock() {
        System.out.println("Plant: " + plantStock + " kgs");
        System.out.println("Meat: " + meatStock + " kgs");
        System.out.println("Fish: " + fishStock + " kgs");
    }

    /**
     * Finds a person by ID.
     * @param id ID of the person.
     * @return Person object if found, null otherwise.
     */
    private Person findPerson(String id) {
        for (Person person : persons) {
            if (person.getId().equals(id))
                return person;
        }
        return null;
    }

    /**
     * Finds an animal by name.
     * @param name Name of the animal.
     * @return Animal object if found, null otherwise.
     */
    private Animal findAnimal(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equals(name))
                return animal;
        }
        return null;
    }

    /**
     * Processes commands from a file and writes output to another file.
     * Commands include listing food, animal visitation, feeding, and cleaning.
     *
     * @param commandFile Input file with commands.
     * @param outputFile Output file to write results.
     */
    public void processCommands(String commandFile, String outputFile) {
        try (
                BufferedReader br = new BufferedReader(new FileReader(commandFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))
        ) {
            // Initialization logs
            writer.write("***********************************\n");
            writer.write("***Initializing Animal information***\n");
            for (Animal animal : animals) {
                writer.write("Added new " + animal.getClass().getSimpleName() +
                        " with name " + animal.getName() +
                        " aged " + animal.getAge() + ".\n");
            }

            writer.write("***********************************\n");
            writer.write("***Initializing Visitor and Personnel information***\n");
            for (Person person : persons) {
                String type = person instanceof Personnel ? "Personnel" : "Visitor";
                writer.write("Added new " + type +
                        " with id " + person.getId() +
                        " and name " + person.getName() + ".\n");
            }

            writer.write("***********************************\n");
            writer.write("***Initializing Food Stock***\n");
            writer.write("There are " + String.format("%.3f", round(meatStock)).replace(",", ".") + " kg of Meat in stock\n");
            writer.write("There are " + String.format("%.3f", round(fishStock)).replace(",", ".") + " kg of Fish in stock\n");
            writer.write("There are " + String.format("%.3f", round(plantStock)).replace(",", ".") + " kg of Plant in stock\n");

            // Processing commands
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String command = parts[0];

                writer.write("***********************************\n");
                writer.write("***Processing new Command***\n");

                if (command.equalsIgnoreCase("List Food Stock")) {
                    writer.write("Listing available Food Stock:\n");
                    writer.write("Plant: " + String.format("%.3f", round(plantStock)).replace(",", ".") + " kgs\n");
                    writer.write("Fish: " + String.format("%.3f", round(fishStock)).replace(",", ".") + " kgs\n");
                    writer.write("Meat: " + String.format("%.3f", round(meatStock)).replace(",", ".") + " kgs\n");

                } else if (command.equalsIgnoreCase("Animal Visitation")) {
                    if (parts.length < 3) {
                        writer.write("Error processing command: " + line + "\n");
                        writer.write("Error: Command has insufficient arguments.\n");
                        continue;
                    }

                    String id = parts[1];
                    String name = parts[2];
                    Person person = findPerson(id);
                    Animal animal = findAnimal(name);

                    if (person == null) {
                        writer.write("Error: There are no visitors or personnel with the id " + id + "\n");
                    } else if (animal == null) {
                        writer.write(person.getName() + " attempts to clean " + name + "'s habitat.\n");
                        writer.write("Error: There are no animals with the name " + name + ".\n");
                    } else {
                        writer.write(person.visitAnimal(animal) + "\n");
                    }

                } else if (command.equalsIgnoreCase("Feed Animal")) {
                    if (parts.length < 4) {
                        writer.write("Error processing command: " + line + "\n");
                        writer.write("Error: Command has insufficient arguments.\n");
                        continue;
                    }

                    String id = parts[1];
                    String name = parts[2];
                    int numberOfMeals;

                    try {
                        numberOfMeals = Integer.parseInt(parts[3]);
                    } catch (NumberFormatException e) {
                        writer.write("Error processing command: " + line + "\n");
                        writer.write("Error: For input string: \"" + parts[3] + "\"\n");
                        continue;
                    }

                    Person person = findPerson(id);
                    Animal animal = findAnimal(name);

                    if (person == null) {
                        writer.write("Error: There are no visitors or personnel with the id " + id + "\n");
                    } else if (animal == null) {
                        writer.write(person.getName() + " attempts to feed " + name + ".\n");
                        writer.write("Error: There are no animals with the name " + name + ".\n");
                    } else {
                        writer.write(person.feedAnimal(this, animal, numberOfMeals));
                    }
                } else {
                    writer.write("Error: Unknown command: " + command + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rounds a given double to three decimal places.
     * @param value The value to round.
     * @return Rounded value.
     */
    private static double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
