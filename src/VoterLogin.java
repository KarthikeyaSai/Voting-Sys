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

            File voterFile = new File("Voters_acc/" + username + ".txt");
            if (!voterFile.exists()) {
                JOptionPane.showMessageDialog(loginPanel, "No user found with the given username.");
                System.out.println("No user file found for username: " + username);
                return;
            }

            try (Scanner scanner = new Scanner(new FileReader(voterFile))) {
                if (scanner.hasNextLine()) {
                    String storedPassword = scanner.nextLine();
                    if (password.equals(storedPassword)) {
                        System.out.println("Password matched for username: " + username);
                        for (Voter voter : UserInterface.votersList) {
                            if (voter.getUsername().equals(username)) {
                                UserInterface.loggedInVoter = voter;
                                break;
                            }
                        }
                        if (UserInterface.loggedInVoter != null) {
                            JOptionPane.showMessageDialog(loginPanel, "Logged in successfully!");
                            cardLayout.show(mainPanel, "Voting");
                        } else {
                            System.out.println("Voter object not found in the list for username: " + username);
                        }
                    } else {
                        JOptionPane.showMessageDialog(loginPanel, "Wrong password.");
                        System.out.println("Wrong password entered for username: " + username);
                    }
                } else {
                    JOptionPane.showMessageDialog(loginPanel, "Password file is empty.");
                    System.out.println("Password file is empty for username: " + username);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        backLoginButton.addActionListener(event -> cardLayout.show(mainPanel, "Home"));
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }
}
