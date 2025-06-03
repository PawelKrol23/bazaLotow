package org.example.bazalotow2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Ticket {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Flight flight;

    @ManyToOne(optional = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime purchaseDate;

    @Column(nullable = false)
    private boolean confirmed = false;
}
