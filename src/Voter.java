public class Voter {
    private String name;
    private String dateOfBirth;
    private String gender;
    private String nationality;
    private String username;
    private String password;
    private boolean hasVoted; // Add this field

    public Voter(String name, String dateOfBirth, String gender, String nationality, String username, String password) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.username = username;
        this.password = password;
        this.hasVoted = false; // Initialize as false
    }

    public String getUsername() {
        return username;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nDate of Birth: " + dateOfBirth + "\nGender: " + gender + "\nNationality: " + nationality;
    }
}
