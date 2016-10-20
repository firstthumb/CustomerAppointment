package com.ekocaman.demo.controller;

import com.ekocaman.demo.exc.InvalidParameterException;
import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Customer;
import com.ekocaman.demo.model.Rating;
import com.ekocaman.demo.request.CustomerRequest;
import com.ekocaman.demo.response.AppointmentResponse;
import com.ekocaman.demo.response.CustomerResponse;
import com.ekocaman.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    private final UserService userService;

    @Autowired
    public CustomerController(UserService userService) {
        this.userService = userService;
    }


    /**
     * As an audiologist I want to create my customers
     *
     * @param customerRequest
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CustomerResponse saveCustomer(@RequestBody CustomerRequest customerRequest) {
        final Customer customer = Customer.withRequest(customerRequest);
        final Customer savedCustomer = userService.saveCustomer(customer);
        return CustomerResponse.withCustomer(savedCustomer);
    }

    /**
     * As a customer I want to see my next appointment
     *
     * @param customerId
     * @return
     */
    @RequestMapping(value = "{customerId}/appointments/next", method = RequestMethod.GET)
    public AppointmentResponse getNextAppointment(@PathVariable("customerId") Long customerId) {
        final Appointment nextAppointment = userService.getNextAppointment(customerId);
        return AppointmentResponse.withAppointment(nextAppointment);
    }

    /**
     * As a customer I want to rate my last appointment
     *
     * @param customerId
     * @param rating
     * @return
     */
    @RequestMapping(value = "/{customerId}/appointments/last/rate/{rating}", method = RequestMethod.POST)
    public AppointmentResponse rateLastAppointment(@PathVariable("customerId") Long customerId, @PathVariable("rating") String rating) {
        try {
            final Appointment appointment = userService.rateLastAppointment(customerId, Rating.valueOf(rating));
            return AppointmentResponse.withAppointment(appointment);
        } catch (IllegalArgumentException exc) {
            throw new InvalidParameterException("Invalid Parameter", exc);
        }
    }
}
