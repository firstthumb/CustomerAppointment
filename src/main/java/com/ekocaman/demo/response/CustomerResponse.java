package com.ekocaman.demo.response;

import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.ArrayList;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableCustomerResponse.class)
@JsonDeserialize(as = ImmutableCustomerResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class CustomerResponse {

    @JsonProperty("customer_id")
    public abstract long getCustomerId();

    @JsonProperty("first_name")
    public abstract String getFirstName();

    @JsonProperty("last_name")
    public abstract String getLastName();

    @JsonProperty("appointments")
    public abstract List<AppointmentResponse> getAppointments();

    public static ImmutableCustomerResponse withCustomer(Customer customer) {
        final ImmutableCustomerResponse.Builder builder = ImmutableCustomerResponse.builder()
                .customerId(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName());


        final List<AppointmentResponse> appointments = new ArrayList<>();
        for (Appointment appointment : customer.getAppointments()) {
            appointments.add(AppointmentResponse.withAppointment(appointment));
        }
        builder.appointments(appointments);


        return builder.build();
    }
}
