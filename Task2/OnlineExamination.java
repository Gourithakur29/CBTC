import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public void updateProfile(String newProfile) {
        profile = newProfile;
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctOption;

    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

public class OnlineExamination{
    private static User currentUser;
    private static Timer timer;
    private static int remainingTime;

    public static void main(String[] args) {
        // Initialize sample users and questions (Replace with database integration)
        User user1 = new User("gourithakur@123", "12345", "Gouri Thakur");
        Question question1 = new Question("What is 2 + 2?", new String[]{"A. 3", "B. 4", "C. 5", "D. 6"}, 1);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Online Exam System");

        // Login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticateUser(username, password, user1)) {
            System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
            startExam(question1);
        } else {
            System.out.println("Login failed. Exiting.");
            System.exit(0);
        }
    }

    private static boolean authenticateUser(String username, String password, User... users) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    private static void startExam(Question question) {
        System.out.println("Exam started.");

        // Set a timer for the exam (e.g., 30 minutes)
        remainingTime = 30 * 60; // Convert to seconds
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingTime--;
                if (remainingTime <= 0) {
                    submitExam();
                }
            }
        }, 1000, 1000); // Timer updates every 1 second

        // Present the MCQ question
        System.out.println(question.getQuestion());
        for (int i = 0; i < question.getOptions().length; i++) {
            System.out.println(question.getOptions()[i]);
        }

        // Get user's answer
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select your answer (A/B/C/D): ");
        String answer = scanner.nextLine().toUpperCase();
        int selectedOption = answer.charAt(0) - 'A';

        if (selectedOption >= 0 && selectedOption < question.getOptions().length) {
            if (selectedOption == question.getCorrectOption()) {
                System.out.println("Correct answer!");
            } else {
                System.out.println("Wrong answer.");
            }
        } else {
            System.out.println("Invalid option.");
        }

        submitExam();
    }

    private static void submitExam() {
        System.out.println("Time's up! Exam submitted.");
        timer.cancel();
        logout();
    }

    private static void logout() {
        System.out.println("Logging out...");
        currentUser = null;
        System.exit(0);
    }
}
