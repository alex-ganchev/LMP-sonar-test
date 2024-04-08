package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.AnswerType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Answer> answerSet;
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;
    @Column(columnDefinition = "TEXT")
    private String question;
    private int points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(Set<Answer> answerSet) {
        this.answerSet = answerSet;
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
}
