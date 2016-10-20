package com.ekocaman.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Audiologist {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "audiologist", fetch = FetchType.EAGER)
    private List<Appointment> appointments;

}
