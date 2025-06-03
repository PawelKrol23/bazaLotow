package org.example.bazalotow2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@Setter
public class Airplane {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String registrationNumber;
    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "airplane")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Flight> flights;
}
