import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class VoterRegistration {
    private JPanel registrationPanel;

    public VoterRegistration(JPanel mainPanel) {
        registrationPanel = new JPanel(new GridLayout(6, 2));
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

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        submitRegButton.addActionListener(event -> {
            String name = nameField.getText();
            String dateOfBirth = dobField.getText();
            String nationality = nationalityField.getText();
            String gender = genderField.getText();

            if (dateOfBirth.length() != 8) {
                JOptionPane.showMessageDialog(registrationPanel, "You have entered the wrong 'DATE OF BIRTH.'");
                return;
            }

            String yearStr = dateOfBirth.substring(4);
            int year;
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(registrationPanel, "You have entered an invalid 'DATE OF BIRTH.'");
                return;
            }

            if (year <= 1907 || year >= 2007) {
                JOptionPane.showMessageDialog(registrationPanel, "You are not old enough to 'VOTE'.");
                return;
            }

            if (!nationality.equalsIgnoreCase("Indian")) {
                JOptionPane.showMessageDialog(registrationPanel, "Only Indian nationals can register.");
                return;
            }

            JOptionPane.showMessageDialog(registrationPanel, "Registration details stored!");

            // Generating username
            String username = name.substring(0, 4) + yearStr;

            // Generating password
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder pass = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                int index = random.nextInt(characters.length());
                pass.append(characters.charAt(index));
            }
            String password = pass.toString();

            Voter voter = new Voter(name, dateOfBirth, gender, nationality, username, password);
            UserInterface.votersList.add(voter);

            // Create directories if they do not exist
            new File("Voters_Info").mkdirs();
            new File("Voters_acc").mkdirs();

            // Check if voter already exists
            File voterFile = new File("Voters_Info/" + name + ".txt");
            if (voterFile.exists()) {
                JOptionPane.showMessageDialog(registrationPanel, "You have already registered.");
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
            JOptionPane.showMessageDialog(registrationPanel, "Registration successful!\nUsername: " + username + "\nPassword: " + password);
            cardLayout.show(mainPanel, "Home");
        });

        backRegButton.addActionListener(event -> cardLayout.show(mainPanel, "Home"));
    }

    public JPanel getRegistrationPanel() {
        return registrationPanel;
    }
}
