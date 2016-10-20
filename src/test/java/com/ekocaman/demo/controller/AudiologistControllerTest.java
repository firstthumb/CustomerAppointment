package com.ekocaman.demo.controller;

import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Audiologist;
import com.ekocaman.demo.model.Customer;
import com.ekocaman.demo.repository.AppointmentRepository;
import com.ekocaman.demo.repository.AudiologistRepository;
import com.ekocaman.demo.repository.CustomerRepository;
import com.ekocaman.demo.request.AppointmentRequest;
import com.ekocaman.demo.request.CustomerRequest;
import com.ekocaman.demo.request.ImmutableAppointmentRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AudiologistControllerTest {

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
    public void testAddAppointmentSuccessfully() throws Exception {
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

        final String url = "/api/v1/audiologists/" + savedAudiologist.getId() + "/appointments";

        final AppointmentRequest request = ImmutableAppointmentRequest.builder()
                .customerId(savedCustomer.getId())
                .date(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .build();

        final String response = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointment_id").value(is(notNullValue())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final AppointmentResponse appointmentResponse = mapper.readValue(response, AppointmentResponse.class);

        assertThat(appointmentResponse, is(notNullValue()));

        final Appointment fetchedAppointment = appointmentRepository.findOne(appointmentResponse.getAppointmentId());
        assertThat(fetchedAppointment, is(notNullValue()));
    }

    @Test
    public void testGetAppointmentsOverviewAndRatingsSuccessfully() throws Exception {

    }

    @Test
    public void testGetNextAppointmentsOverviewSuccessfully() throws Exception {

    }
}
