package com.artur.zti.user.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "zti_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = "Podaj nazwe uzytkownika")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Podaj haslo")
    private String password;

    @NotEmpty(message = "Powtorz haslo")
    private String confirmPassword;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "Podaj adres email")
    private String email;

    @Column(name="role")
    private String role;

    public User(){}

    public User(String username, String password, String confirmPassword, String email, String role) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", confirmPassword='" + confirmPassword + '\'' +
            ", email='" + email + '\'' +
            ", role='" + role + '\'' +
            '}';
    }
}
