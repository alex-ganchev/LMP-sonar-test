package digital.razgrad.LMP.entity;

import digital.razgrad.LMP.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    @NotNull(message = "Полето не може да е празно!")
    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @NotNull(message = "Полето не може да е празно!")
    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String lastName;
    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    @Column(nullable = false, unique = true)
    private String username;
    @Size(min = 5, message = "Минимален брой символи 5!")
    @Column(nullable = false, unique = true)
    private String email;
    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'ROLE_STUDENT'")
    private Role role;
    @Column(columnDefinition = "boolean DEFAULT '1'")
    private boolean enabled;
    @Size(min = 3, message = "Минимален брой символи 3!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    @Column(nullable = false)
    private int age;
    @Size(min = 3, message = "Минимален брой символи 3!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    @Column(nullable = false)
    private String city;

    @Column(nullable = true)
    private String experience;
    @Column(nullable = true)
    private String interests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}

