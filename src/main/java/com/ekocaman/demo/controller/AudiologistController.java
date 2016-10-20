package com.ekocaman.demo.controller;

import com.ekocaman.demo.model.Customer;
import com.ekocaman.demo.request.AppointmentRequest;
import com.ekocaman.demo.request.CustomerRequest;
import com.ekocaman.demo.response.AppointmentOverviewResponse;
import com.ekocaman.demo.response.AppointmentResponse;
import com.ekocaman.demo.response.CustomerResponse;
import com.ekocaman.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audiologists")
public class AudiologistController {
    private static final Logger LOG = LoggerFactory.getLogger(AudiologistController.class);

    private final UserService userService;

    @Autowired
    public AudiologistController(UserService userService) {
        this.userService = userService;
    }


    /**
     * As an audiologist I want to create appointments with a customer
     *
     * @param audiologistId
     * @param appointmentRequest
     * @return
     */
    @RequestMapping(value = "/{audiologistId}/appointments", method = RequestMethod.POST)
    public AppointmentResponse addAppointment(@PathVariable("audiologistId") Long audiologistId, @RequestBody AppointmentRequest appointmentRequest) {
        return null;
    }


    /**
     * As an audiologist I want to see an overview of the next week’s appointments
     *
     * @param audiologistId
     * @param date
     * @return
     */
    @RequestMapping(value = "/{audiologistId}/appointments", method = RequestMethod.GET)
    public List<AppointmentResponse> getAppointmentsByTime(
            @PathVariable("audiologistId") Long audiologistId,
            @RequestParam(value = "date", required = false) String date) {

        return null;
    }

    /**
     * As an audiologist I want to see an overview of all appointments and their ratings
     *
     * @param audiologistId
     * @return
     */
    @RequestMapping(value = "/{audiologistId}/appointments/overview", method = RequestMethod.GET)
    public AppointmentOverviewResponse getAppointmentsOverview(
            @PathVariable("audiologistId") Long audiologistId) {

        return null;
    }
}
