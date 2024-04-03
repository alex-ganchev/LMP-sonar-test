package digital.razgrad.LMP.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, message = "Минимален брой символи 1!")
    @Size(max = 40, message = "Максимален брой символи 40!")
    private String name;
    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;
    private LocalDate startDate;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToMany(mappedBy = "moduleSet")
    private Set<Student> studentSet;
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<Lecture> lectureSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    public Set<Lecture> getLectureSet() {
        return lectureSet;
    }

    public void setLectureSet(Set<Lecture> lectureSet) {
        this.lectureSet = lectureSet;
    }
}
