package com.ekocaman.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableAppointmentResponse.class)
@JsonDeserialize(as = ImmutableAppointmentResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AppointmentOverviewResponse {


}
