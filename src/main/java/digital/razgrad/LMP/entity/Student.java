package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.UserRole;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {

    @ManyToMany
//    @JoinTable(
//            name = "students_tests",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private Set<Test> tests;
    @ManyToMany
//    @JoinTable(
//            name = "students_modules",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private Set<Module> modules;
    @OneToMany(mappedBy = "student")
    private List<TestStudentAnswer> testStudentAnswerList;
    @OneToMany(mappedBy = "student")
    private List<Application> applicationList;
    @ManyToMany
//    @JoinTable(
//            name = "students_courses",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id"))
//      5 часа борба да установя защо релацията ManyToMany не работи и пак не ми е ясно,
//      защо не иска да тръгне с допълнителното конфигуриране :((((
    private Set<Course> courses;

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
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
