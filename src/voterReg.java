import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class voterReg extends Voter {

                                    //    AN ARRAY LIST WHICH CONTAINS THE VOTER OBJECTS

    static ArrayList<Voter> votersList = new ArrayList<>();

                                //    REGISTRATION METHOD WHICH INITIATES A VOTER OBJECT

    public static Voter registration() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Please enter your date of birth: ");
        String dateOfBirth = scanner.nextLine();
        int year = Integer.parseInt(dateOfBirth.substring(4));

        int age = 2024 - year;
        if (age < 18 || dateOfBirth.length() != 8 || !( 1907 < year && year < 2024)) {
            System.out.println("Your are not old enough to 'VOTE' or has entered wrong 'DATE OF BIRTH.'" +
                    " PLEASE ENTER AGAIN");
            return  registration();
        }

        System.out.print("Please enter your gender: ");
        String gender = scanner.nextLine();

        System.out.print("Please enter your nationality: ");
        String nationality = scanner.nextLine();

                                        //        READING THE NUMBER OF VOTERS FILE
        numberVoter = getNumberVoter();
        numberVoter++;
        setNumberVoter();
        System.out.println(numberVoter);

                                    //        CALLING THE RESPECTIVE METHODS FOR RESPECTIVE WORKING

        String username = generateUsername(name, dateOfBirth);

                                    //        CODE FOR CHECKING IF THE VOTER EXISTS OR NOT

        try {
            File myObj = new File("Voters_Info/" + name + ".txt");
            Scanner myReader = new Scanner(myObj);
            System.out.println("You have already registered");
            myReader.close();
            return registration();
        } catch (FileNotFoundException e) {
            System.out.println("You are getting registered");
        }



        String password = generatePassword(8);
        Voter voter = new Voter(name, dateOfBirth, age, gender, nationality, username, password);
        voter.ID = numberVoter;
        votersList.add(voter);
        fileWriting(voter);
        userFile(voter);
        return voter;
    }

                        //    A FILE WRITER METHOD WHICH ENTERS THE GIVEN INFORMATION INTO A TEXT FILE

    protected static void fileWriting(Voter voter) {
        String info  = voter.toString();
            try {
                // to create a file with the username as the file name,
                // It will be easier to search for the object
                FileWriter information = new FileWriter(
                        "Voters_Info/" + voter.getName() + ".txt");
                // Writing into file
                information.write(info);
                // Closing the file writing connection
                information.close();
                // Display message for successful execution of
                // program on the console
                System.out.println(
                        "YOU ARE REGISTERED FOR THE ELECTION!!!!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
    protected static void userFile(Voter voter) {
        String pass =  voter.getPassword();{
            try {
                // to create a file with the username as the file name,
                // It will be easier to search for the object
                FileWriter user = new FileWriter(
                        "Voters_acc/" + voter.getUsername() + ".txt");

                // Writing into file
                user.write(pass);
                // Closing the file writing connection
                user.close();

                // Display message for successful execution of
                // program on the console
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

