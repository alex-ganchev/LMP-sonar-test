package digital.razgrad.LMP.constant;


public enum UserRole {
    ROLE_STUDENT("Курсист"),
    ROLE_TEACHER ("Преподавател"),
    ROLE_ADMIN ("Администратор");

    private String label;
    UserRole(String label){
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}

