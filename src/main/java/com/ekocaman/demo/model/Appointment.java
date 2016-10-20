package com.ekocaman.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@ToString(exclude = {"customer", "audiologist", "reviews"})
@EqualsAndHashCode(exclude = {"customer", "audiologist", "reviews"})
@Entity
public class Appointment {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "audiologistId")
    private Audiologist audiologist;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.EAGER)
    private Set<Review> reviews;
}
