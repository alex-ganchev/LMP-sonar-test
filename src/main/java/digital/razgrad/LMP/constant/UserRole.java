package digital.razgrad.LMP.constant;


public enum UserRole {
    ROLE_ADMIN ("Администратор"),
    ROLE_TEACHER ("Преподавател"),
    ROLE_STUDENT("Курсист");
    private String label;
    UserRole(String label){
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}

