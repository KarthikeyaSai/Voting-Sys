import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class VoterLogin {
    private JPanel loginPanel;

    public VoterLogin(JPanel mainPanel) {
        loginPanel = new JPanel(new GridLayout(4, 2));

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

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        submitLoginButton.addActionListener(event -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            System.out.println("Attempting to log in with username: " + username);

            // Check if the voter file exists
            File voterFile = new File("Voters_acc/" + username + ".txt");
            if (!voterFile.exists()) {
                JOptionPane.showMessageDialog(loginPanel, "No user found with the given username.");
                return;
            }

            try (Scanner scanner = new Scanner(new FileReader(voterFile))) {
                if (scanner.hasNextLine()) {
                    String storedPassword = scanner.nextLine();
                    if (password.equals(storedPassword)) {
                        UserInterface.loggedInVoter = getVoterByUsername(username);
                        if (UserInterface.loggedInVoter != null) {
                            JOptionPane.showMessageDialog(loginPanel, "Logged in successfully!");
                            cardLayout.show(mainPanel, "Voting");
                        }
                        else {
                            JOptionPane.showMessageDialog(loginPanel, "You have already voted!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(loginPanel, "Wrong password.");
                    }
                } else {
                    JOptionPane.showMessageDialog(loginPanel, "Password file is empty.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        backLoginButton.addActionListener(event -> cardLayout.show(mainPanel, "Home"));
    }

    private Voter getVoterByUsername(String username) {
        for (Voter voter : UserInterface.votersList) {
            if (voter.getUsername().equals(username)) {
                return voter;
            }
        }
        return null;
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }
}
