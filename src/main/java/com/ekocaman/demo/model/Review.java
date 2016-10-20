package com.ekocaman.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@ToString(exclude = {"customer", "appointment"})
@EqualsAndHashCode(exclude = {"customer", "appointment"})
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Appointment appointment;

    @ManyToOne(optional = false)
    private Customer customer;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Rating rating;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date reviewDate;
}
