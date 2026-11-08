import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseManager {
    private final List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        if (findCourse(course.getCode()) != null) {
            throw new IllegalArgumentException("A course with that code already exists.");
        }
        courses.add(course);
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public Course findCourse(String code) {
        String normalizedCode = Course.normalizeCode(code);
        return findCourseRecursive(normalizedCode, 0);
    }

    private Course findCourseRecursive(String code, int index) {
        if (index >= courses.size()) {
            return null;
        }
        Course course = courses.get(index);
        if (course.getCode().equals(code)) {
            return course;
        }
        return findCourseRecursive(code, index + 1);
    }

    public int getTotalUnits() {
        int total = 0;
        for (Course course : courses) {
            total += course.getUnits();
        }
        return total;
    }

    public void saveToFile(Path file) throws IOException {
        List<String> rows = new ArrayList<>();
        for (Course course : courses) {
            rows.add(course.getCode() + "|" + course.getTitle() + "|" + course.getUnits());
        }
        Files.write(file, rows, StandardCharsets.UTF_8);
    }

    public void loadFromFile(Path file) throws IOException {
        List<String> rows = Files.readAllLines(file, StandardCharsets.UTF_8);
        List<Course> loadedCourses = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            String[] fields = rows.get(i).split("\\|", -1);
            if (fields.length != 3) {
                throw new IOException("Invalid course data on line " + (i + 1) + ".");
            }
            try {
                Course course = new Course(fields[0], fields[1], Integer.parseInt(fields[2].trim()));
                for (Course existing : loadedCourses) {
                    if (existing.getCode().equals(course.getCode())) {
                        throw new IllegalArgumentException("Duplicate course code.");
                    }
                }
                loadedCourses.add(course);
            } catch (IllegalArgumentException exception) {
                throw new IOException("Invalid course data on line " + (i + 1) + ": "
                        + exception.getMessage(), exception);
            }
        }
        courses.clear();
        courses.addAll(loadedCourses);
    }
}
