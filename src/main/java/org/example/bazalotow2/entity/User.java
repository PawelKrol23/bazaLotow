package org.example.bazalotow2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, updatable = false)
    private String authority;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;
}
