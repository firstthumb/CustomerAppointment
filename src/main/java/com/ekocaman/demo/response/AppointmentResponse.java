package com.ekocaman.demo.response;

import com.ekocaman.demo.model.Appointment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
@JsonSerialize(as = ImmutableAppointmentResponse.class)
@JsonDeserialize(as = ImmutableAppointmentResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AppointmentResponse {

    @JsonProperty("appointment_id")
    public abstract long getAppointmentId();

    @JsonProperty("date")
    public abstract Date getDate();

    public static ImmutableAppointmentResponse withAppointment(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return ImmutableAppointmentResponse.builder()
                .appointmentId(appointment.getId())
                .date(appointment.getDate())
                .build();
    }
}
