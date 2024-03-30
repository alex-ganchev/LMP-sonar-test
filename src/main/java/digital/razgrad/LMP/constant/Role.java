package digital.razgrad.LMP.constant;


public enum Role {
    ROLE_ADMIN ("Администратор"),
    ROLE_TEACHER ("Преподавател"),
    ROLE_STUDENT("Курсист");
    private String label;
    Role(String label){
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}

