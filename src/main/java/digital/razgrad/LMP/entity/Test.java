package digital.razgrad.LMP.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name="tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "testSet")
    private Set<Student> studentSet;
    @OneToOne(mappedBy = "test")
    private Lecture lecture;
}
