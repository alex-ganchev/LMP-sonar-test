package digital.razgrad.LMP.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "testSet")
    private Set<Student> studentSet;
    //@OneToOne(mappedBy = "test")
    @OneToOne
    private Lecture lecture;
    @Size(min = 1, message = "Минимален брой символи 1!")
    @Size(max = 40, message = "Максимален брой символи 40!")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Min(value = 1, message = "Въведете минути!")
    private int time;
    @Min(value = 1, message = "Въведете брой въпроси!")
    private int questionsNumber;
    @OneToMany(mappedBy = "test")
    private List<TestResult> testResultList;
    @Column(columnDefinition = "boolean DEFAULT '0'", nullable = false)
    private boolean isMandatory;
    @Min(value = 1, message = "Минималният процент е 1!")
    @Max(value = 100, message = "Максималният процент е 100!")
    @Column(name = "min_passing_percentage", nullable = false)
    private int minPassingPercentage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getQuestionsNumber() {
        return questionsNumber;
    }

    public void setQuestionsNumber(int questionsNumber) {
        this.questionsNumber = questionsNumber;
    }

    public List<TestResult> getTestResultList() {
        return testResultList;
    }

    public void setTestResultList(List<TestResult> testResultList) {
        this.testResultList = testResultList;
    }
}
