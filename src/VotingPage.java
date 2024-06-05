import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VotingPage {
    private JPanel votingPanel;

    public VotingPage(JPanel mainPanel) {
        votingPanel = new JPanel(new GridLayout(5, 1));

        JLabel instructionLabel = new JLabel("Select a candidate to vote for:");
        votingPanel.add(instructionLabel);

        String[] candidates = {"Candidate1", "Candidate2", "Candidate3", "Candidate4"};
        ButtonGroup candidateGroup = new ButtonGroup();
        JRadioButton[] candidateButtons = new JRadioButton[candidates.length];

        for (int i = 0; i < candidates.length; i++) {
            String candidate = candidates[i];
            String manifesto = getManifesto(candidate);
            JRadioButton candidateButton = new JRadioButton(candidate + " - " + manifesto);
            candidateButtons[i] = candidateButton;
            candidateGroup.add(candidateButton);
            votingPanel.add(candidateButton);
        }

        JButton voteButton = new JButton("Vote");
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
                    String candidate = candidateButton.getText().split(" - ")[0];
                    System.out.println("Voter selected: " + candidate);
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
            System.out.println("Manifesto file not found for candidate: " + candidate);
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
                System.out.println(manifesto);
            }
            if (scanner.hasNextLine()) {
                System.out.println(votes);
                votes = Integer.parseInt(scanner.nextLine());
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }

        votes++;

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(manifesto + "\n");
            writer.write(Integer.toString(votes));
            System.out.println("Updated vote count for " + candidate + ": " + votes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public JPanel getVotingPanel() {
        return votingPanel;
    }
}
