import javax.swing.*;
import java.awt.*;
public class AdminInterface extends JFrame {
    private JTextArea displayArea;

    public AdminInterface() {
        setTitle("Admin Interface");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        mainPanel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(5, 0, 5, 0);
        gbcButton.gridx = 0;
        gbcButton.gridy = GridBagConstraints.RELATIVE;
        gbcButton.fill = GridBagConstraints.HORIZONTAL;

        JButton viewVotesButton = new JButton("View Votes");
        JButton viewVoterButton = new JButton("View Voter Info");
        JButton totalVotesButton = new JButton("Total Votes");
        JButton declareWinnerButton = new JButton("Declare Winner");

        buttonPanel.add(viewVotesButton, gbcButton);
        buttonPanel.add(viewVoterButton, gbcButton);
        buttonPanel.add(totalVotesButton, gbcButton);
        buttonPanel.add(declareWinnerButton, gbcButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        mainPanel.add(buttonPanel, gbc);

        viewVotesButton.addActionListener(e -> viewVotes());
        viewVoterButton.addActionListener(e -> viewVoterInfo());
        totalVotesButton.addActionListener(e -> displayTotalVotes());
        declareWinnerButton.addActionListener(e -> declareWinner());

        add(mainPanel);
    }

    private void viewVotes() {
        String candidate = JOptionPane.showInputDialog(this, "Enter candidate name:");
        if (candidate != null && !candidate.trim().isEmpty()) {
            int votes = Admin.candidatesVotes(candidate);
            displayArea.setText("Votes for " + candidate + ": " + votes);
        } else {
            displayArea.setText("No candidate name provided.");
        }
    }

    private void viewVoterInfo() {
        String voter = JOptionPane.showInputDialog(this, "Enter voter name:");
        if (voter != null && !voter.trim().isEmpty()) {
            String info = Admin.viewVoter(voter);
            displayArea.setText("Voter Info for " + voter + ":\n" + info);
        } else {
            displayArea.setText("No voter name provided.");
        }
    }

    private void displayTotalVotes() {
        int totalVotes = Admin.totalVotes();
        displayArea.setText("Total Votes: " + totalVotes);
    }

    private void declareWinner() {
        String winner = Admin.declare();
        displayArea.setText(winner);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminInterface ai = new AdminInterface();
            ai.setVisible(true);
        });
    }
}