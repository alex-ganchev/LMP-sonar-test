package digital.razgrad.LMP.dto;

import digital.razgrad.LMP.constant.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
    private Long id;
    private UserRole userRole;

    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String firstName;

    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String lastName;

    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String username;
    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String email;

    private String password;

    private String repeatPassword;
    @NotNull
    private int age;
    @Size(min = 3, message = "Минимален брой символи 3!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String city;

    private String experience;

    private String interests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
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
