import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Complaint {
    String neighborhood;
    String coordinates;
    String issueType;
    String description;

    Complaint(String neighborhood, String coordinates, String issueType, String description) {
        this.neighborhood = neighborhood;
        this.coordinates = coordinates;
        this.issueType = issueType;
        this.description = description;
    }
}

class App {
    private List<Complaint> complaints;

    public App() {
        complaints = new ArrayList<>();
        loadComplaints();
    }

    public void submitComplaint(String user, String neighborhood, String coordinates, String issueType, String description) {
        Complaint newComplaint = new Complaint(neighborhood, coordinates, issueType, description);
        complaints.add(newComplaint);
        saveComplaintToFile(newComplaint);
        System.out.println("Complaint submitted successfully and saved to file.");
    }

    private void saveComplaintToFile(Complaint complaint) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("complaints.txt", true))) {
            writer.write(complaint.neighborhood + "," + complaint.coordinates + "," + complaint.issueType + "," + complaint.description);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Unable to open file.");
        }
    }

    public void viewComplaints(String neighborhood) {
        System.out.println("Complaints for neighborhood: " + neighborhood);
        for (Complaint complaint : complaints) {
            if (complaint.neighborhood.equals(neighborhood)) {
                System.out.println("Location: " + complaint.coordinates);
                System.out.println("Issue Type: " + complaint.issueType);
                System.out.println("Description: " + complaint.description);
                System.out.println("----------------------");
            }
        }
    }

    private void loadComplaints() {
        try (BufferedReader reader = new BufferedReader(new FileReader("complaints.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    complaints.add(new Complaint(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to open file.");
        }
    }
}

public class MunicipalityApp {
    public static void main(String[] args) {
        App municipalityApp = new App();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter neighborhood: ");
        String neighborhood = scanner.nextLine();

        System.out.print("Enter coordinates: ");
        String coordinates = scanner.nextLine();

        System.out.print("Enter issue type (lighting, roads, etc.): ");
        String issueType = scanner.nextLine();

        System.out.print("Describe the issue: ");
        String description = scanner.nextLine();

        municipalityApp.submitComplaint(username, neighborhood, coordinates, issueType, description);

        System.out.println("\nView complaints for a neighborhood.");
        System.out.print("Enter neighborhood name: ");
        neighborhood = scanner.nextLine();
        municipalityApp.viewComplaints(neighborhood);

        scanner.close();
    }
}
