package digital.razgrad.LMP.constant;

public enum CourseStatus {
    ACTIVE("Активен"),
    STARTED("Започнал"),
    FINISHED("Приключил"),
    CANCELLED("Отменен"),
    DELAYED("Отложен");
    private String label;

    CourseStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
