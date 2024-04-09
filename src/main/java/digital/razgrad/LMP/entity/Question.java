package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.AnswerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "question")
    private List<Answer> answerList;
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    @Column(columnDefinition = "TEXT")
    private String question;

    private int points;
    @OneToMany(mappedBy = "question")
    private List<TestStudentAnswer> testStudentAnswerList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<TestStudentAnswer> getTestStudentAnswerList() {
        return testStudentAnswerList;
    }

    public void setTestStudentAnswerList(List<TestStudentAnswer> testStudentAnswerList) {
        this.testStudentAnswerList = testStudentAnswerList;
    }
}
