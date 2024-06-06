import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Voter {
    protected   int ID;
    private String name;
    private String dateOfBirth;
    private int age;
    private String gender;
    private String nationality;

    protected String username;
    protected String password;

                                        //    THESE ARE THE GETTER FOR THE FIELDS OF VOTER

    public String getName() {
        System.out.println(name);
        return name;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    protected static int numberVoter;

                                            //    CONSTRUCTORS FOR VOTERS FOR VARIOUS USE

    public Voter() {

    }
    public Voter(String name, String dateOfBirth, int age, String gender,
                 String nationality, String username, String password)
    {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.username = username;
        this.password = password;
    }
    public Voter(String name, String dateOfBirth, int age, String gender, String nationality) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

                                        //    GENERATING THE PASSWORD AND USERNAME METHODS

    public static String generateUsername(String name, String dateOfBirth) {
        String year = dateOfBirth.substring(4);
        return name + year;
    }
    public static String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public static int getNumberVoter() {
        int data = 0;
        try {
            File myObj = new File("numberVoter.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = Integer.parseInt(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
    protected static void setNumberVoter() {
        int data = numberVoter;
        try {
            // Create a FileWriter object to write to "numberVoter.txt"
            FileWriter information = new FileWriter("numberVoter.txt");
            // Convert the integer to a string and write it to the file
            information.write(Integer.toString(data));
            // Close the FileWriter
            information.close();
            // Display message for successful execution
            System.out.println("Voter is added");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}