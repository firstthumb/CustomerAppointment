package com.ekocaman.demo.model;

import com.ekocaman.demo.request.CustomerRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"appointments", "reviews"})
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Review> reviews;

    public static Customer withRequest(CustomerRequest customerRequest) {
        final Customer customer = new Customer();

        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());

        return customer;
    }

    public static Customer withIdAndRequest(long customerId, CustomerRequest customerRequest) {
        final Customer customer = new Customer();

        customer.setId(customerId);
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());

        return customer;
    }
}
