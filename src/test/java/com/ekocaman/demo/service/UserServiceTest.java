package com.ekocaman.demo.service;

import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Audiologist;
import com.ekocaman.demo.model.Customer;
import com.ekocaman.demo.repository.CustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateCustomerSuccessfully() {
        final Customer customer = new Customer();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        final Customer savedCustomer = userService.saveCustomer(customer);

        assertThat(savedCustomer, is(notNullValue()));
        assertThat(savedCustomer.getId(), is(notNullValue()));
    }

    @Test
    public void testAddAppointmentSuccessfully() {
        // Create Audiologist
        Audiologist audiologist = new Audiologist();
        audiologist.setFirstName(UUID.randomUUID().toString());
        audiologist.setLastName(UUID.randomUUID().toString());
        audiologist = userService.saveAudiologist(audiologist);

        Customer customer = new Customer();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer = userService.saveCustomer(customer);

        assertThat(customer, is(notNullValue()));
        assertThat(customer.getId(), is(notNullValue()));

        final Appointment appointment = userService.saveAppointment(audiologist.getId(), customer.getId(), new Date());

        customer.setAppointments(newArrayList(appointment));
        customer = userService.saveCustomer(customer);

        final Customer fetchedCustomer = userService.findByCustomerId(customer.getId());
        assertThat(fetchedCustomer, is(notNullValue()));
        assertThat(fetchedCustomer.getAppointments().size(), is(1));
    }

    @Test
    public void testGetNextWeekAppointments() {
        // Create Audiologist
        Audiologist audiologist = new Audiologist();
        audiologist.setFirstName(UUID.randomUUID().toString());
        audiologist.setLastName(UUID.randomUUID().toString());
        audiologist = userService.saveAudiologist(audiologist);

        assertThat(audiologist, is(notNullValue()));
        assertThat(audiologist.getId(), is(notNullValue()));

        // Create Customer
        Customer customer = new Customer();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer = userService.saveCustomer(customer);

        assertThat(customer, is(notNullValue()));
        assertThat(customer.getId(), is(notNullValue()));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        final Date nextWeekDate = calendar.getTime();

        // Next Week Appointments
        for (int i = 0; i < 10; i++) {
            userService.saveAppointment(audiologist.getId(), customer.getId(), new Date(nextWeekDate.getTime() + 5 * 60 * 1000 * (i + 1)));
        }

        calendar.add(Calendar.WEEK_OF_MONTH, -2);
        final Date lastWeekDate = calendar.getTime();
        // Last Week Appointments
        for (int i = 0; i < 10; i++) {
            userService.saveAppointment(audiologist.getId(), customer.getId(), new Date(lastWeekDate.getTime() - 5 * 60 * 1000 * (i + 1)));
        }

        List<Appointment> nextWeekAppointments = userService.getNextWeekAppointments(audiologist.getId());
        assertThat(nextWeekAppointments.size(), is(10));
    }

    @Test
    public void testGetCustomerNextAppointment() {
        // Create Audiologist
        Audiologist audiologist = new Audiologist();
        audiologist.setFirstName(UUID.randomUUID().toString());
        audiologist.setLastName(UUID.randomUUID().toString());
        audiologist = userService.saveAudiologist(audiologist);

        assertThat(audiologist, is(notNullValue()));
        assertThat(audiologist.getId(), is(notNullValue()));

        // Create Customer
        Customer customer = new Customer();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer = userService.saveCustomer(customer);

        assertThat(customer, is(notNullValue()));
        assertThat(customer.getId(), is(notNullValue()));

        Appointment firstAppointment = null;
        for (int i = 0; i < 10; i++) {
            // Create Appointment
            Appointment appointment = userService.saveAppointment(audiologist.getId(), customer.getId(), new Date(System.currentTimeMillis() + 5 * 60 * 1000 * (i + 1)));

            if (i == 0) {
                firstAppointment = appointment;
            }
        }

        Appointment nextAppointment = userService.getNextAppointment(customer.getId());
        assertThat(nextAppointment.getId(), is(firstAppointment.getId()));
    }

    @Test
    public void testGetCustomerLastAppointment() {
        // Create Audiologist
        Audiologist audiologist = new Audiologist();
        audiologist.setFirstName(UUID.randomUUID().toString());
        audiologist.setLastName(UUID.randomUUID().toString());
        audiologist = userService.saveAudiologist(audiologist);

        assertThat(audiologist, is(notNullValue()));
        assertThat(audiologist.getId(), is(notNullValue()));

        // Create Customer
        Customer customer = new Customer();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer = userService.saveCustomer(customer);

        assertThat(customer, is(notNullValue()));
        assertThat(customer.getId(), is(notNullValue()));

        Appointment lastAppointment = null;
        for (int i = 0; i < 10; i++) {
            // Create Appointment
            Appointment appointment = userService.saveAppointment(audiologist.getId(), customer.getId(), new Date(System.currentTimeMillis() - 5 * 60 * 1000 * (i + 1)));

            if (i == 0) {
                lastAppointment = appointment;
            }
        }

        Appointment nextAppointment = userService.getLastAppointment(customer.getId());
        assertThat(nextAppointment.getId(), is(lastAppointment.getId()));
    }
}
