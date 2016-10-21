package com.ekocaman.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableAppointmentOverviewResponse.class)
@JsonDeserialize(as = ImmutableAppointmentOverviewResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AppointmentOverviewResponse {

    @JsonProperty("appointments")
    public abstract List<AppointmentResponse> getAppointments();
}
