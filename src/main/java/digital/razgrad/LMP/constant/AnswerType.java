package digital.razgrad.LMP.constant;

public enum AnswerType {
    OPEN("Отворен"),
    ONE("Един верен"),
    MANY("Няколко верни");
    private String label;

    AnswerType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
