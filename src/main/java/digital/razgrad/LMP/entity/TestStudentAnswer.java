package digital.razgrad.LMP.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tests_students_answers")
public class TestStudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "test_result_id")
    private TestResult testResult;

    @ManyToOne
    @JoinColumn(name = "student_id")
    //Може да се премахне, добавен е student към testResult
    private Student student;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    private String answer;
    private boolean isCorrect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
