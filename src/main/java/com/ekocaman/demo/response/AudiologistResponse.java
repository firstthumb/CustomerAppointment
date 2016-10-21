package com.ekocaman.demo.response;

import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Audiologist;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.ArrayList;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableAudiologistResponse.class)
@JsonDeserialize(as = ImmutableAudiologistResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AudiologistResponse {

    @JsonProperty("audiologist_id")
    public abstract long getAudiologistId();

    @JsonProperty("first_name")
    public abstract String getFirstName();

    @JsonProperty("last_name")
    public abstract String getLastName();

    @JsonProperty("appointments")
    public abstract List<AppointmentResponse> getAppointments();

    public static ImmutableAudiologistResponse withAudiologist(Audiologist audiologist) {
        final ImmutableAudiologistResponse.Builder builder = ImmutableAudiologistResponse.builder()
                .audiologistId(audiologist.getId())
                .firstName(audiologist.getFirstName())
                .lastName(audiologist.getLastName());

        final List<AppointmentResponse> appointments = new ArrayList<>();
        if (audiologist.getAppointments() != null) {
            for (Appointment appointment : audiologist.getAppointments()) {
                appointments.add(AppointmentResponse.withAppointment(appointment));
            }
        }
        builder.appointments(appointments);

        return builder.build();
    }
}
