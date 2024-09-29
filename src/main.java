import java.io.*;
import java.util.*;

public class main {

    // Class to hold each dictionary entry
    static class DictionaryEntry {
        String tagalog;
        String cebuano;
        String english;

        DictionaryEntry(String tagalog, String cebuano, String english) {
            this.tagalog = tagalog;
            this.cebuano = cebuano;
            this.english = english;
        }
    }

    static List<DictionaryEntry> dictionary = new ArrayList<>();
    static List<DictionaryEntry> filteredList = new ArrayList<>(); // List to store the filtered entries

    public static void main(String[] args) {
        // Load the dictionary from the provided file
        loadDictionary("/Users/lance/Downloads/DataStructuresAndAlgorithms-CCDATARCL/Activities/DSA-CebuanoToTagalog/src/filtered_combined_translation_filter_2.txt");

        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true) {
            // Prompt the user to input a letter or terminate the program
            System.out.println("Type the letter to get words starting with it or type 'ayaw ko na' to quit:");
            userInput = scanner.nextLine().trim().toUpperCase();

            if (userInput.equalsIgnoreCase("ayaw ko na")) {
                System.out.println("Thank you for using the Dictionary!");
                break; // Terminate the program
            }

            // Ensure the user entered a single letter
            if (userInput.length() != 1 || !Character.isLetter(userInput.charAt(0))) {
                System.out.println("Please enter a valid letter.");
                continue; // Restart the loop
            }

            char letter = userInput.charAt(0);

            // List Cebuano words starting with the specified letter
            listWordsStartingWith(letter);

            // Ask the user to pick one word
            if (!filteredList.isEmpty()) {
                System.out.println("Pick a word by entering its number:");

                // Handle invalid input for word selection
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice, please enter a number.");
                    continue;
                }

                if (choice > 0 && choice <= filteredList.size()) {
                    DictionaryEntry selectedEntry = filteredList.get(choice - 1);
                    System.out.println("Cebuano: " + selectedEntry.cebuano);
                    System.out.println("Tagalog: " + selectedEntry.tagalog);
                    System.out.println("English: " + selectedEntry.english);
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        }
        scanner.close();
    }

    // Function to load the dictionary from the file
    public static void loadDictionary(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String tagalog = "", cebuano = "", english = "";
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Tagalog: ")) {
                    tagalog = line.replace("Tagalog: ", "").trim();
                } else if (line.startsWith("Cebuano: ")) {
                    cebuano = line.replace("Cebuano: ", "").trim();
                } else if (line.startsWith("English: ")) {
                    english = line.replace("English: ", "").trim();
                    // Add to dictionary when all parts are found
                    dictionary.add(new DictionaryEntry(tagalog, cebuano, english));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Function to list Cebuano words starting with the specified letter
    public static void listWordsStartingWith(char letter) {
        filteredList.clear(); // Clear the previous filtered list
        int index = 1; // To show the word numbers
        for (DictionaryEntry entry : dictionary) {
            if (entry.cebuano.toUpperCase().charAt(0) == letter) {
                filteredList.add(entry);
                System.out.println(index + ". " + entry.cebuano);
                index++;
            }
        }
        if (filteredList.isEmpty()) {
            System.out.println("No words found starting with letter " + letter);
        }
    }
}
