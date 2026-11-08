public class Course {
    private final String code;
    private final String title;
    private final int units;

    public Course(String code, String title, int units) {
        this.code = normalizeCode(code);
        this.title = normalizeTitle(title);
        if (units <= 0) {
            throw new IllegalArgumentException("Units must be greater than zero.");
        }
        this.units = units;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getUnits() {
        return units;
    }

    public static String normalizeCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty.");
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.contains("|") || normalized.contains("\n") || normalized.contains("\r")) {
            throw new IllegalArgumentException("Course code cannot contain | or a line break.");
        }
        return normalized;
    }

    public static String normalizeTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be empty.");
        }
        String normalized = title.trim();
        if (normalized.contains("|") || normalized.contains("\n") || normalized.contains("\r")) {
            throw new IllegalArgumentException("Course title cannot contain | or a line break.");
        }
        return normalized;
    }
}
