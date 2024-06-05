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
        homePanel.add(registrationButton);
        homePanel.add(loginButton);

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
    }

}