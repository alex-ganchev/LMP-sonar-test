package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.UserRole;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {

    @ManyToMany
    @JoinTable(
            name = "students_tests",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private Set<Test> testSet;
    @ManyToMany
    @JoinTable(
            name = "students_modules",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private Set<Module> moduleSet;
    @OneToMany(mappedBy = "student")
    private List<TestStudentAnswer> testStudentAnswerList;
    @OneToMany(mappedBy = "student")
    private List<Application> applicationList;

    public Student() {
        setUserRole(UserRole.ROLE_STUDENT);
    }

    public Set<Test> getTestSet() {
        return testSet;
    }

    public void setTestSet(Set<Test> testSet) {
        this.testSet = testSet;
    }

    public Set<Module> getModuleSet() {
        return moduleSet;
    }

    public void setModuleSet(Set<Module> moduleSet) {
        this.moduleSet = moduleSet;
    }

    public List<TestStudentAnswer> getTestStudentAnswerList() {
        return testStudentAnswerList;
    }

    public void setTestStudentAnswerList(List<TestStudentAnswer> testStudentAnswerList) {
        this.testStudentAnswerList = testStudentAnswerList;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }
}
