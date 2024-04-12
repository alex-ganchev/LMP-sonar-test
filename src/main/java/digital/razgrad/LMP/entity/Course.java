package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.CourseStatus;
import digital.razgrad.LMP.constant.CourseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, message = "Минимален брой символи 1!")
    @Size(max = 40, message = "Максимален брой символи 40!")
    private String name;
    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "course")
    @OrderBy("name")
    private Set<Module> moduleSet;
   // @ManyToMany(mappedBy = "courseSet")
    @ManyToMany
    @OrderBy("firstName,lastName")
    private Set<Teacher> teachers;
    @Enumerated(EnumType.STRING)
    private CourseType type;
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
    @Column(nullable = true)
    private String city;
    @NotNull(message =  "Въведете дата!")
    private LocalDate startDate;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Application> applicationSet;
    //@ManyToMany(mappedBy = "courses")
    // 5 часа борба да установя защо релацията ManyToMany не работи и пак не ми е ясно,
    // защо не иска да тръгне с допълнителното конфигуриране :((((
    @ManyToMany
    private Set<Student> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Module> getModuleSet() {
        return moduleSet;
    }

    public void setModuleSet(Set<Module> moduleSet) {
        this.moduleSet = moduleSet;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Application> getApplicationSet() {
        return applicationSet;
    }

    public void setApplicationSet(Set<Application> applicationSet) {
        this.applicationSet = applicationSet;
    }

    public Set<Student> getStudentSet() {
        return students;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.students = studentSet;
    }
}
