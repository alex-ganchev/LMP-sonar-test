package digital.razgrad.LMP.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {


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

    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String password;

    @Size(min = 4, message = "Минимален брой символи 4!")
    @Size(max = 20, message = "Максимален брой символи 20!")
    private String repeatPassword;
    @NotNull
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
