package org.example.bazalotow2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private City departureCity;

    @ManyToOne(optional = false)
    private City arrivalCity;

    @Column(nullable = false)
    private LocalDateTime departureDateTime;
    @Column(nullable = false)
    private int availableSeats;

    @ManyToOne(optional = false)
    private Airplane airplane;

    @OneToMany(mappedBy = "flight")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Ticket> tickets;
}
