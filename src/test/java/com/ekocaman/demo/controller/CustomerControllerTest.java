package com.ekocaman.demo.controller;

import com.ekocaman.demo.model.*;
import com.ekocaman.demo.repository.AppointmentRepository;
import com.ekocaman.demo.repository.AudiologistRepository;
import com.ekocaman.demo.repository.CustomerRepository;
import com.ekocaman.demo.request.CustomerRequest;
import com.ekocaman.demo.request.ImmutableCustomerRequest;
import com.ekocaman.demo.response.AppointmentResponse;
import com.ekocaman.demo.response.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AudiologistRepository audiologistRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSaveCustomerSuccessfully() throws Exception {
        String url = "/api/v1/customers";

        final String firstName = UUID.randomUUID().toString();
        final String lastName = UUID.randomUUID().toString();

        final CustomerRequest request = ImmutableCustomerRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        final String response = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_id").value(is(notNullValue())))
                .andExpect(jsonPath("$.first_name").value(is(firstName)))
                .andExpect(jsonPath("$.last_name").value(is(lastName)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final CustomerResponse customerResponse = mapper.readValue(response, CustomerResponse.class);

        assertThat(customerResponse, is(notNullValue()));
    }

    @Test
    public void testSaveCustomerWithEmptyNameFail() throws Exception {
        final String url = "/api/v1/customers";

        final String request = "{'first_name':null, 'last_name':null}";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetNextAppointmentSuccessfully() throws Exception {
        // Create Audiologist
        final Audiologist audiologist = new Audiologist();
        audiologist.setFirstName(UUID.randomUUID().toString());
        audiologist.setLastName(UUID.randomUUID().toString());
        final Audiologist savedAudiologist = audiologistRepository.save(audiologist);

        // Create Customer
        final Customer customer = new Customer();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        final Customer savedCustomer = customerRepository.save(customer);

        // Create Next Appointment
        Appointment firstAppointment = null;
        for (int i = 0; i < 10; i++) {
            final Appointment appointment = new Appointment();
            appointment.setAudiologist(savedAudiologist);
            appointment.setCustomer(savedCustomer);
            appointment.setDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000 * (i + 1)));
            appointmentRepository.save(appointment);

            if (i == 0) {
                firstAppointment = appointment;
            }
        }

        final String url = "/api/v1/customers/" + savedCustomer.getId() + "/appointments/next";

        final String response = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointment_id").value(is(firstAppointment.getId().intValue())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final AppointmentResponse appointmentResponse = mapper.readValue(response, AppointmentResponse.class);

        assertThat(appointmentResponse, is(notNullValue()));
    }

    @Test
    public void testRateLastAppointmentSuccessfully() throws Exception {
        // Create Audiologist
        final Audiologist audiologist = new Audiologist();
        audiologist.setFirstName(UUID.randomUUID().toString());
        audiologist.setLastName(UUID.randomUUID().toString());
        final Audiologist savedAudiologist = audiologistRepository.save(audiologist);

        // Create Customer
        final Customer customer = new Customer();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        final Customer savedCustomer = customerRepository.save(customer);

        // Create Appointment
        final Appointment appointment = new Appointment();
        appointment.setAudiologist(savedAudiologist);
        appointment.setCustomer(savedCustomer);
        appointment.setDate(new Date(System.currentTimeMillis() - 5 * 60 * 1000));
        final Appointment savedAppointment = appointmentRepository.save(appointment);

        final String url = "/api/v1/customers/" + savedCustomer.getId() + "/appointments/last/rate/" + Rating.AVERAGE;

        final String response = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointment_id").value(is(savedAppointment.getId().intValue())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final AppointmentResponse appointmentResponse = mapper.readValue(response, AppointmentResponse.class);

        assertThat(appointmentResponse, is(notNullValue()));

        // Check Review
        final Appointment fetchedAppointment = appointmentRepository.findOne(appointment.getId());

        assertThat(fetchedAppointment, is(notNullValue()));
        assertThat(fetchedAppointment.getReviews().size(), is(1));
        for (Review review : fetchedAppointment.getReviews()) {
            assertThat(review.getRating(), is(Rating.AVERAGE));
        }
    }
}
