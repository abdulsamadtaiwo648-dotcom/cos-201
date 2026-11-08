import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class CourseManagerTest {
    public static void main(String[] args) throws IOException {
        Path dataFile = Files.createTempFile("courses-test", ".txt");
        try {
            CourseManager manager = new CourseManager();
            manager.addCourse(new Course(" cos 201 ", " Programming I ", 3));
            manager.addCourse(new Course("MTH 101", "Mathematics", 2));
            check(manager.getCourses().size() == 2, "courses are added");
            check(manager.findCourse("COS 201").getTitle().equals("Programming I"),
                    "search trims and ignores code casing");
            check(manager.getTotalUnits() == 5, "units are totaled");

            manager.saveToFile(dataFile);
            CourseManager restored = new CourseManager();
            restored.loadFromFile(dataFile);
            check(restored.getCourses().size() == 2, "courses save and load");
            check(restored.getTotalUnits() == 5, "loaded courses retain units");

            try {
                manager.addCourse(new Course("cos 201", "Duplicate", 1));
                throw new AssertionError("duplicate course code should be rejected");
            } catch (IllegalArgumentException expected) {
                check(true, "duplicate course code is rejected");
            }

            Files.write(dataFile, Arrays.asList("BAD|Broken|not-a-number"), StandardCharsets.UTF_8);
            try {
                restored.loadFromFile(dataFile);
                throw new AssertionError("invalid file should be rejected");
            } catch (IOException expected) {
                check(restored.getCourses().size() == 2,
                        "failed load leaves existing courses unchanged");
            }
            System.out.println("All CourseManager tests passed.");
        } finally {
            Files.deleteIfExists(dataFile);
        }
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("Failed: " + description);
        }
        System.out.println("Passed: " + description);
    }
}
