package com.hieuhoang.springrest.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import java.util.Collections;


@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name="uk_users_email", columnNames = "email"))
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank @Column(nullable = false)
    private String name;


    @NotBlank @Email @Column(nullable = false)
    private String email;


    @NotBlank @Column(nullable = false)
    private String password; // BCrypt-hashed


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = Collections.singleton(Role.USER);


    public AppUser() {}


    public AppUser(String name, String email, String password, Set<Role> roles) {
        this.name = name; this.email = email; this.password = password; this.roles = roles;
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
