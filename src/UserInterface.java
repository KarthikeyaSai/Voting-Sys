import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class UserInterface extends JFrame {
    // Variables to store registration data
    private String name;
    private String dateOfBirth;
    private String nationality;
    private String gender;

    // Variables to store login data
    private String username;
    private String password;
    static ArrayList<Voter> votersList = new ArrayList<>();

    public UserInterface() {
        setTitle("User Interface");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with card layout
        JPanel mainPanel = new JPanel(new CardLayout());

        // Home panel with buttons
        JPanel homePanel = new JPanel();
        JButton registrationButton = new JButton("Registration");
        JButton loginButton = new JButton("Login");
        homePanel.add(registrationButton);
        homePanel.add(loginButton);

        // Registration panel
        JPanel registrationPanel = new JPanel(new GridLayout(6, 2));
        registrationPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        registrationPanel.add(nameField);

        registrationPanel.add(new JLabel("Date of Birth (ddMMyyyy):"));
        JTextField dobField = new JTextField();
        registrationPanel.add(dobField);

        registrationPanel.add(new JLabel("Nationality:"));
        JTextField nationalityField = new JTextField();
        registrationPanel.add(nationalityField);

        registrationPanel.add(new JLabel("Gender:"));
        JTextField genderField = new JTextField();
        registrationPanel.add(genderField);

        JButton submitRegButton = new JButton("Submit");
        registrationPanel.add(submitRegButton);

        JButton backRegButton = new JButton("Back");
        registrationPanel.add(backRegButton);

        // Login panel
        JPanel loginPanel = new JPanel(new GridLayout(4, 2));
        loginPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton submitLoginButton = new JButton("Submit");
        loginPanel.add(submitLoginButton);

        JButton backLoginButton = new JButton("Back");
        loginPanel.add(backLoginButton);

        // Add panels to main panel
        mainPanel.add(homePanel, "Home");
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(loginPanel, "Login");

        // Set initial panel
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "Home");

        // Add main panel to frame
        add(mainPanel);

        // Action listeners
        registrationButton.addActionListener(e -> cardLayout.show(mainPanel, "Registration"));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        submitRegButton.addActionListener(event -> {
            name = nameField.getText();
            dateOfBirth = dobField.getText();
            nationality = nationalityField.getText();
            gender = genderField.getText();

            if (dateOfBirth.length() != 8) {
                JOptionPane.showMessageDialog(this, "You have entered the wrong 'DATE OF BIRTH.'");
                return;
            }

            String yearStr = dateOfBirth.substring(4);
            int year;
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "You have entered an invalid 'DATE OF BIRTH.'");
                return;
            }

            if (year <= 1907 || year >= 2006) {
                JOptionPane.showMessageDialog(this, "You are not old enough to 'VOTE'.");
                return;
            }

            if (!nationality.equalsIgnoreCase("Indian")) {
                JOptionPane.showMessageDialog(this, "Only Indian nationals can register.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Registration details stored!");
            cardLayout.show(mainPanel, "Home");
            // Generating username
            username = name.substring(0, 4) + yearStr;

            // Generating password
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder pass = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                int index = random.nextInt(characters.length());
                pass.append(characters.charAt(index));
            }
            password = pass.toString();

            Voter voter = new Voter(name, dateOfBirth, gender, nationality, username, password);
            votersList.add(voter);


            // Create directories if they do not exist
            new File("Voters_Info").mkdirs();
            new File("Voters_acc").mkdirs();

            // Check if voter already exists
            File voterFile = new File("Voters_Info/" + name + ".txt");
            if (voterFile.exists()) {
                JOptionPane.showMessageDialog(this, "You have already registered.");
                return;
            }

            // Write voter information to file
            try (FileWriter information = new FileWriter(voterFile)) {
                information.write(voter.toString());
                System.out.println("YOU ARE REGISTERED FOR THE ELECTION!!!!");
                System.out.println(voter);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Write voter account information
            try (FileWriter user = new FileWriter("Voters_acc/" + voter.getUsername() + ".txt")) {
                user.write(password);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Display username and password
            JOptionPane.showMessageDialog(this, "Registration successful!\nUsername: " + username + "\nPassword: " + password);
        });

        submitLoginButton.addActionListener(event -> {
            username = usernameField.getText();
            password = new String(passwordField.getPassword());

            File voterFile = new File("Voters_acc/" + username + ".txt");
            if (!voterFile.exists()) {
                JOptionPane.showMessageDialog(this, "No user found with the given username.");
                return;
            }

            try (Scanner scanner = new Scanner(new FileReader("Voters_acc/" + username + ".txt"))) {
                if (scanner.hasNextLine()) {
                    String storedPassword = scanner.nextLine();
                    if (password.equals(storedPassword)) {
                        JOptionPane.showMessageDialog(this, "Logged in successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Wrong password.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Password file is empty.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            cardLayout.show(mainPanel, "Home");
        });

        backRegButton.addActionListener(event -> cardLayout.show(mainPanel, "Home"));
        backLoginButton.addActionListener(event -> cardLayout.show(mainPanel, "Home"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserInterface().setVisible(true));
    }
}