import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Admin {
    private String username = "Admin371";
    private String password = "naaMg";
    private static int totalVotes;

    // Constructor
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.totalVotes = 0;
    }

    // Method to read candidate votes from a file
    public static int candidatesVotes(String candidate) {
        int votes = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(candidate + ".txt"))) {
            br.readLine(); // Skip the first line (manifesto)
            votes = Integer.parseInt(br.readLine()); // Read the number of votes
        } catch (IOException e) {
            e.printStackTrace();
        }
        return votes;
    }

    // Method to view voter information from a file
    public static String viewVoter(String voter) {
        StringBuilder voterInfo = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("Voters_Info/" + voter + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                voterInfo.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voterInfo.toString();
    }

    // Method to calculate the total votes of all candidates
    public static int totalVotes() {
        totalVotes = 0;
        Path dir = Paths.get("."); // Current directory

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
            for (Path entry : stream) {
                if (entry.getFileName().toString().startsWith("Candidate")) {
                    totalVotes += candidatesVotes(entry.toString().replace(".txt", ""));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalVotes;
    }

    // Method to declare the winner
    public static String declare() {
        Path dir = Paths.get("."); // Current directory
        int highestVotes = -1;
        String winner = "No candidates found";

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
            for (Path entry : stream) {
                if (entry.getFileName().toString().startsWith("Candidate")) {
                    String candidate = entry.toString().replace(".txt", "");
                    int candidateVotes = candidatesVotes(candidate);
                    candidate = candidate.replace("./", " ");
                    if (candidateVotes > highestVotes) {
                        highestVotes = candidateVotes;
                        winner = candidate;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "The winner is " + winner + " with " + highestVotes + " votes.";
    }

}
