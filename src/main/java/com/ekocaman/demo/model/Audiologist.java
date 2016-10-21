package com.ekocaman.demo.model;

import com.ekocaman.demo.request.AudiologistRequest;
import com.ekocaman.demo.request.CustomerRequest;
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

    public static Audiologist withRequest(AudiologistRequest audiologistRequest) {
        final Audiologist audiologist = new Audiologist();

        audiologist.setFirstName(audiologistRequest.getFirstName());
        audiologist.setLastName(audiologistRequest.getLastName());

        return audiologist;
    }
}
