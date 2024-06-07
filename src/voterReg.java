import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class voterReg extends Voter {

    // An ArrayList which contains the Voter objects
    static ArrayList<Voter> votersList = new ArrayList<>();

    // Registration method which initiates a Voter object
    public static Voter registration() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();

        String dateOfBirth = null;
        int year = 0;
        while (true) {
            try {
                System.out.print("Please enter your date of birth (DDMMYYYY): ");
                dateOfBirth = scanner.nextLine();
                if (dateOfBirth.length() != 8) {
                    throw new IllegalArgumentException("Date of birth must be in DDMMYYYY format.");
                }
                year = Integer.parseInt(dateOfBirth.substring(4));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid date format. Please try again.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        int age = 2024 - year;
        if (age < 18 || !(1907 < year && year < 2024)) {
            System.out.println("You are not old enough to vote or have entered an incorrect date of birth. Please try again.");
            return registration();
        }

        System.out.print("Please enter your gender: ");
        String gender = scanner.nextLine();

        System.out.print("Please enter your nationality: ");
        String nationality = scanner.nextLine();

        // Reading the number of voters file
        numberVoter = getNumberVoter();
        numberVoter++;
        setNumberVoter();
        System.out.println(numberVoter);

        // Calling the respective methods for respective working
        String username = generateUsername(name, dateOfBirth);

        // Code for checking if the voter exists or not
        try {
            File myObj = new File("Voters_Info/" + name + ".txt");
            Scanner myReader = new Scanner(myObj);
            System.out.println("You have already registered.");
            myReader.close();
            return registration();
        } catch (FileNotFoundException e) {
            System.out.println("You are getting registered.");
        }

        String password = generatePassword(8);
        Voter voter = new Voter(name, dateOfBirth, age, gender, nationality, username, password);
        voter.ID = numberVoter;
        votersList.add(voter);
        fileWriting(voter);
        userFile(voter);
        return voter;
    }

    // A file writer method which enters the given information into a text file
    protected static void fileWriting(Voter voter) {
        String info = voter.toString();
        try {
            // to create a file with the username as the file name,
            // It will be easier to search for the object
            FileWriter information = new FileWriter("Voters_Info/" + voter.getName() + ".txt");
            // Writing into file
            information.write(info);
            // Closing the file writing connection
            information.close();
            // Display message for successful execution of
            // program on the console
            System.out.println("YOU ARE REGISTERED FOR THE ELECTION!!!!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    protected static void userFile(Voter voter) {
        String pass = voter.getPassword();
        try {
            // to create a file with the username as the file name,
            // It will be easier to search for the object
            FileWriter user = new FileWriter("Voters_acc/" + voter.getUsername() + ".txt");

            // Writing into file
            user.write(pass);
            // Closing the file writing connection
            user.close();

            // Display message for successful execution of
            // program on the console
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
