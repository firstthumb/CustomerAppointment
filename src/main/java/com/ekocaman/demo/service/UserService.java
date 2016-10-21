package com.ekocaman.demo.service;

import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Audiologist;
import com.ekocaman.demo.model.Customer;
import com.ekocaman.demo.model.Rating;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public interface UserService {

    public Audiologist saveAudiologist(@NotNull Audiologist audiologist);


    public Customer saveCustomer(@NotNull Customer customer);

    public Customer findByCustomerId(@NotNull Long customerId);

    public List<Appointment> getAppointments(long audiologistId);

    public Appointment saveAppointment(long audiologistId, long customerId, Date date);

    public List<Appointment> getNextWeekAppointments(long audiologistId);

    public Appointment getNextAppointment(long customerId);

    public Appointment getLastAppointment(long customerId);

    public Appointment rateLastAppointment(long customerId, Rating rating);

}
