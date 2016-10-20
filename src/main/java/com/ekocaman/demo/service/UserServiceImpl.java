package com.ekocaman.demo.service;

import com.ekocaman.demo.exc.CustomerNotFoundException;
import com.ekocaman.demo.model.*;
import com.ekocaman.demo.repository.AppointmentRepository;
import com.ekocaman.demo.repository.AudiologistRepository;
import com.ekocaman.demo.repository.CustomerRepository;
import com.ekocaman.demo.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final CustomerRepository customerRepository;
    private final AudiologistRepository audiologistRepository;
    private final AppointmentRepository appointmentRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public UserServiceImpl(CustomerRepository customerRepository, AudiologistRepository audiologistRepository, AppointmentRepository appointmentRepository, ReviewRepository reviewRepository) {
        this.customerRepository = customerRepository;
        this.audiologistRepository = audiologistRepository;
        this.appointmentRepository = appointmentRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Audiologist saveAudiologist(@NotNull Audiologist audiologist) {
        LOG.info("Saving audiologist ==> {}", audiologist);

        Objects.requireNonNull(audiologist, "Audiologist cannot be null");

        final Audiologist savedAudiologist = audiologistRepository.save(audiologist);
        LOG.info("Save audiologist result : {}", savedAudiologist);

        return savedAudiologist;
    }

    @Override
    public Customer saveCustomer(@NotNull Customer customer) {
        LOG.info("Saving customer ==> {}", customer);

        Objects.requireNonNull(customer, "Customer cannot be null");

        final Customer savedCustomer = customerRepository.save(customer);
        LOG.info("Save customer result : {}", savedCustomer);

        return savedCustomer;
    }

    @Override
    public Customer findByCustomerId(@NotNull Long customerId) {
        LOG.info("Finding customer by Id : {}", customerId);

        Objects.requireNonNull(customerId, "CustomerId cannot be null");

        final Customer customer = customerRepository.findOne(customerId);
        LOG.info("Customer found : {}", customer);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with Id : " + customerId + " could not be found");
        }

        return customer;
    }

    @Override
    public Appointment saveAppointment(long audiologistId, long customerId, Date date) {
        LOG.info("Saving appointment ==> {} {} {}", audiologistId, customerId, date);

        Objects.requireNonNull(date, "Date cannot be null");

        final Audiologist audiologist = audiologistRepository.findOne(audiologistId);
        Objects.requireNonNull(audiologist, "Audiologist does not exists");

        final Customer customer = customerRepository.findOne(customerId);
        Objects.requireNonNull(customer, "Customer does not exists");

        final Appointment appointment = new Appointment();
        appointment.setAudiologist(audiologist);
        appointment.setCustomer(customer);
        appointment.setDate(date);

        final Appointment savedAppointment = appointmentRepository.save(appointment);
        LOG.info("Save appointment result : {}", savedAppointment);

        return savedAppointment;
    }

    @Override
    public List<Appointment> getNextWeekAppointments(long audiologistId) {
        LOG.info("Next week appointments ==> {}", audiologistId);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        final Date startDate = calendar.getTime();
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        final Date endDate = calendar.getTime();

        List<Appointment> nextWeekAppointments = appointmentRepository.findByAudiologistIdAndDateBetweenOrderByDateAsc(audiologistId, startDate, endDate);
        LOG.info("Next week appointments result : {}", nextWeekAppointments);

        return nextWeekAppointments;
    }

    @Override
    public Appointment getNextAppointment(long customerId) {
        LOG.info("Next appointment ==> {}", customerId);

        final Appointment nextAppointment = appointmentRepository.findFirstByCustomerIdAndDateAfterOrderByDateAsc(customerId, new Date());
        LOG.info("Next appointment result : {}", nextAppointment);

        return nextAppointment;
    }

    @Override
    public Appointment getLastAppointment(long customerId) {
        LOG.info("Last appointment ==> {}", customerId);

        final Appointment nextAppointment = appointmentRepository.findFirstByCustomerIdAndDateBeforeOrderByDateDesc(customerId, new Date());
        LOG.info("Last appointment result : {}", nextAppointment);

        return nextAppointment;
    }

    @Override
    public Appointment rateLastAppointment(long customerId, Rating rating) {
        LOG.info("Rate last appointment ==> {} {}", customerId, rating);

        final Appointment lastAppointment = getLastAppointment(customerId);

        if (lastAppointment != null) {
            final Customer customer = customerRepository.findOne(customerId);

            final Review review = new Review();
            review.setCustomer(customer);
            review.setAppointment(lastAppointment);
            review.setRating(rating);
            review.setReviewDate(new Date());
            reviewRepository.save(review);
        }

        return lastAppointment;
    }
}
