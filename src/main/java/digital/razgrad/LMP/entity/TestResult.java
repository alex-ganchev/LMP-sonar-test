package digital.razgrad.LMP.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tests_results")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    @OneToMany(mappedBy = "testResult")
    private List<TestStudentAnswer> testStudentAnswerList;
    private int result;
    @Column(name = "test_passed", columnDefinition = "boolean DEFAULT '0'", nullable = false)
    private boolean isTestPassed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<TestStudentAnswer> getTestStudentAnswerList() {
        return testStudentAnswerList;
    }

    public void setTestStudentAnswerList(List<TestStudentAnswer> testStudentAnswerList) {
        this.testStudentAnswerList = testStudentAnswerList;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
