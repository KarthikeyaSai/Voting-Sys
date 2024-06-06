import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VotingPage {
    private JPanel votingPanel;

    public VotingPage(JPanel mainPanel) {
        votingPanel = new JPanel();
        votingPanel.setLayout(new BoxLayout(votingPanel, BoxLayout.Y_AXIS));
        votingPanel.setBackground(new Color(255, 255, 255));
        votingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel instructionLabel = new JLabel("Select a candidate to vote for:");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        instructionLabel.setForeground(new Color(51, 102, 255));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        votingPanel.add(instructionLabel);

        votingPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing

        String[] candidates = {"Candidate1", "Candidate2", "Candidate3", "Candidate4"};
        ButtonGroup candidateGroup = new ButtonGroup();
        JRadioButton[] candidateButtons = new JRadioButton[candidates.length];

        for (int i = 0; i < candidates.length; i++) {
            String candidate = candidates[i];
            String manifesto = getManifesto(candidate);
            JRadioButton candidateButton = new JRadioButton(candidate + " - " + manifesto);
            candidateButton.setFont(new Font("Arial", Font.PLAIN, 14));
            candidateButton.setForeground(new Color(0, 51, 102));
            candidateButton.setBackground(new Color(240, 240, 240));
            candidateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            candidateButtons[i] = candidateButton;
            candidateGroup.add(candidateButton);
            votingPanel.add(candidateButton);
        }

        votingPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing

        JButton voteButton = new JButton("Vote");
        voteButton.setFont(new Font("Arial", Font.BOLD, 16));
        voteButton.setBackground(new Color(51, 102, 255));
        voteButton.setForeground(Color.WHITE);
        voteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        votingPanel.add(voteButton);

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        voteButton.addActionListener(event -> {
            if (UserInterface.loggedInVoter.hasVoted()) {
                JOptionPane.showMessageDialog(votingPanel, "You have already voted.");
                return;
            }

            boolean voteCast = false;
            for (JRadioButton candidateButton : candidateButtons) {
                if (candidateButton.isSelected()) {
                    String candidate = candidateButton.getText().split(" - ")[0].trim();
                    updateVoteCount(candidate);
                    UserInterface.loggedInVoter.setVoted(true);
                    voteCast = true;
                    JOptionPane.showMessageDialog(votingPanel, "You have voted for " + candidate);
                    cardLayout.show(mainPanel, "Home");
                    break;
                }
            }

            if (!voteCast) {
                JOptionPane.showMessageDialog(votingPanel, "Please select a candidate.");
            }
        });
    }

    private String getManifesto(String candidate) {
        File file = new File(candidate + ".txt");
        if (!file.exists()) {
            return "No manifesto available";
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            } else {
                return "No manifesto available";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error reading manifesto";
        }
    }

    private void updateVoteCount(String candidate) {
        File file = new File(candidate + ".txt");
        int votes = 0;
        String manifesto = "";

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                manifesto = scanner.nextLine();
            }
            if (scanner.hasNextLine()) {
                votes = Integer.parseInt(scanner.nextLine());
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }

        votes++;

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(manifesto + "\n");
            writer.write(Integer.toString(votes));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public JPanel getVotingPanel() {
        return votingPanel;
    }
}
