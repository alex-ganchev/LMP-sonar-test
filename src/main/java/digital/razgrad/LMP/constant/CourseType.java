package digital.razgrad.LMP.constant;

public enum CourseType {
    DISTANCE("Дистанционен"),
    ATTENDANCE("Присъствен"),
    COMBINED("Комбиниран");
    private String label;

    CourseType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
