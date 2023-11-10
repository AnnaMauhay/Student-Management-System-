package futurewomen.UserManagement;

public enum Role {
    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student");
    private final String label;
    Role(String label){
        this.label=label;
    }

    public String getLabel() {
        return label;
    }
}
