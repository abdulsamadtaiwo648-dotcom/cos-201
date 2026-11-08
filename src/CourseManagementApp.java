import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CourseManagementApp {
    private static final Path DATA_FILE = Paths.get("courses.txt");

    public static void main(String[] args) {
        CourseManager manager = new CourseManager();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                showMenu();
                System.out.print("Choose an option (1-7): ");
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1":
                        addCourse(scanner, manager);
                        break;
                    case "2":
                        viewCourses(manager);
                        break;
                    case "3":
                        searchCourse(scanner, manager);
                        break;
                    case "4":
                        System.out.println("Total units: " + manager.getTotalUnits());
                        break;
                    case "5":
                        saveCourses(manager);
                        break;
                    case "6":
                        loadCourses(manager);
                        break;
                    case "7":
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Enter a number from 1 to 7.");
                }
                System.out.println();
            }
        }
    }

    private static void showMenu() {
        System.out.println("=== Student Course Management System ===");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit Program");
    }

    private static void addCourse(Scanner scanner, CourseManager manager) {
        try {
            System.out.print("Course code: ");
            String code = scanner.nextLine();
            System.out.print("Course title: ");
            String title = scanner.nextLine();
            System.out.print("Credit units: ");
            int units = Integer.parseInt(scanner.nextLine().trim());
            manager.addCourse(new Course(code, title, units));
            System.out.println("Course added successfully.");
        } catch (NumberFormatException exception) {
            System.out.println("Invalid units. Enter a whole number.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Could not add course: " + exception.getMessage());
        }
    }

    private static void viewCourses(CourseManager manager) {
        if (manager.getCourses().isEmpty()) {
            System.out.println("No courses have been recorded.");
            return;
        }
        System.out.printf("%-15s %-35s %s%n", "COURSE CODE", "COURSE TITLE", "UNITS");
        System.out.println("----------------------------------------------------------------");
        for (Course course : manager.getCourses()) {
            System.out.printf("%-15s %-35s %d%n",
                    course.getCode(), course.getTitle(), course.getUnits());
        }
    }

    private static void searchCourse(Scanner scanner, CourseManager manager) {
        try {
            System.out.print("Enter course code: ");
            Course course = manager.findCourse(scanner.nextLine());
            if (course == null) {
                System.out.println("Course not found.");
            } else {
                System.out.println(course.getCode() + " - " + course.getTitle()
                        + " (" + course.getUnits() + " units)");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid course code: " + exception.getMessage());
        }
    }

    private static void saveCourses(CourseManager manager) {
        try {
            manager.saveToFile(DATA_FILE);
            System.out.println("Courses saved to " + DATA_FILE.toAbsolutePath());
        } catch (IOException exception) {
            System.out.println("Could not save courses: " + exception.getMessage());
        }
    }

    private static void loadCourses(CourseManager manager) {
        try {
            manager.loadFromFile(DATA_FILE);
            System.out.println("Loaded " + manager.getCourses().size() + " course(s) from "
                    + DATA_FILE.toAbsolutePath());
        } catch (IOException exception) {
            System.out.println("Could not load courses: " + exception.getMessage());
        }
    }
}
