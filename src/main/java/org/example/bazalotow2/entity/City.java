package org.example.bazalotow2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class City {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String country;

    @OneToMany(mappedBy = "departureCity")
    private List<Flight> departingFlights;

    @OneToMany(mappedBy = "arrivalCity")
    private List<Flight> arrivingFlights;
}
