package digital.razgrad.LMP.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {

    @OneToMany(mappedBy = "student")
//    @JoinTable(
//            name = "students_tests",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<TestResult> testResults;
    @ManyToMany
//    @JoinTable(
//            name = "students_modules",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private Set<Module> modules;

    @OneToMany(mappedBy = "student")
    //Може да се премахне, добавен е student към testResult
    private List<TestStudentAnswer> testStudentAnswerList;
    @OneToMany(mappedBy = "student")
    private List<Application> applicationList;
    @ManyToMany
//    @JoinTable(
//            name = "students_courses",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;

    public List<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
