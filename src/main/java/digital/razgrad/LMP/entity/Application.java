package digital.razgrad.LMP.entity;

import jakarta.persistence.*;

@Entity
@Table(name="applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false, unique = true)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false, unique = true)
    private Course course;
    @Column(columnDefinition = "boolean DEFAULT '0'", nullable = false)
    private boolean isApproved;

    public Application() {
    }

    public Application(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
