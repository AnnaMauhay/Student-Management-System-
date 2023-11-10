package futurewomen.CourseManagement;

public enum CourseTitle {
    SCIENCE("Science"),
    MATH("Math"),
    LANGUAGE("Language"),
    PE("PE"),
    HISTORY("History");
    private final String label;
    CourseTitle(String label){
        this.label=label;
    }
    public String getLabel() {
        return label;
    }
}
