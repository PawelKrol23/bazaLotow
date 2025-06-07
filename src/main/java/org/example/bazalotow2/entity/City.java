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
public class City {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String country;

    @OneToMany(mappedBy = "departureCity")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Flight> departingFlights;

    @OneToMany(mappedBy = "arrivalCity")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Flight> arrivingFlights;
}
