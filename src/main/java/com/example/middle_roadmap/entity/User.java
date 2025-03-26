package com.example.middle_roadmap.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@Slf4j
@NamedEntityGraph(
        name = "User.devicesAndRole",
        attributeNodes = {
            @NamedAttributeNode("devices"),
            @NamedAttributeNode("role")
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @BatchSize(size = 2)
    @Fetch(FetchMode.SUBSELECT)
    private List<Device> devices;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @Transient
    private String fullName;

    @PrePersist
    public void logNewUserAttempt() {
        log.info("Attempting to add new user with username: " + username);
    }

    @PostLoad
    public void logUserLoad() {
        fullName = name + " " + username;
    }
}
