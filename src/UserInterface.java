import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserInterface extends JFrame {
    static ArrayList<Voter> votersList = new ArrayList<>();
    static Voter loggedInVoter = null;

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
        JButton adminButton = new JButton("Admin");
        homePanel.add(registrationButton);
        homePanel.add(loginButton);
        homePanel.add(adminButton);

        // Registration panel
        VoterRegistration voterRegistration = new VoterRegistration(mainPanel);
        JPanel registrationPanel = voterRegistration.getRegistrationPanel();

        // Login panel
        VoterLogin voterLogin = new VoterLogin(mainPanel);
        JPanel loginPanel = voterLogin.getLoginPanel();

        // Voting panel
        VotingPage votingPage = new VotingPage(mainPanel);
        JPanel votingPanel = votingPage.getVotingPanel();

        // Add panels to main panel
        mainPanel.add(homePanel, "Home");
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(votingPanel, "Voting");

        // Set initial panel
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "Home");

        // Add main panel to frame
        add(mainPanel);

        // Action listeners
        registrationButton.addActionListener(e -> cardLayout.show(mainPanel, "Registration"));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        adminButton.addActionListener(e -> showAdminLoginDialog(mainPanel, cardLayout));
    }

    private void showAdminLoginDialog(JPanel mainPanel, CardLayout cardLayout) {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Admin Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.equals("Admin371") && password.equals("naaMg")) {
                // Open admin interface if credentials are correct
                AdminInterface adminInterface = new AdminInterface();
                adminInterface.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserInterface ui = new UserInterface();
            ui.setVisible(true);
        });
    }
}