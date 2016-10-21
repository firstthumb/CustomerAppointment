package com.ekocaman.demo.controller;

import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Audiologist;
import com.ekocaman.demo.request.AppointmentRequest;
import com.ekocaman.demo.request.AudiologistRequest;
import com.ekocaman.demo.response.*;
import com.ekocaman.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public AudiologistResponse saveCustomer(@RequestBody AudiologistRequest audiologistRequest) {
        final Audiologist audiologist = Audiologist.withRequest(audiologistRequest);
        final Audiologist savedAudiologist = userService.saveAudiologist(audiologist);
        return AudiologistResponse.withAudiologist(savedAudiologist);
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
        final Appointment appointment = userService.saveAppointment(audiologistId, appointmentRequest.getCustomerId(), appointmentRequest.getDate());
        return AppointmentResponse.withAppointment(appointment);
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

        if (date != null && date.equalsIgnoreCase("NextWeek")) {
            final List<Appointment> nextWeekAppointments = userService.getNextWeekAppointments(audiologistId);

            final List<AppointmentResponse> response = new ArrayList<>();
            for (Appointment appointment : nextWeekAppointments) {
                response.add(AppointmentResponse.withAppointment(appointment));
            }
            return response;
        }

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

        final List<Appointment> appointments = userService.getAppointments(audiologistId);

        final List<AppointmentResponse> responses = new ArrayList<>();
        for (Appointment appointment : appointments) {
            responses.add(AppointmentResponse.withAppointment(appointment));
        }

        return ImmutableAppointmentOverviewResponse.builder()
                .appointments(responses)
                .build();
    }
}
