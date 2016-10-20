package com.ekocaman.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
@JsonSerialize(as = ImmutableAppointmentRequest.class)
@JsonDeserialize(as = ImmutableAppointmentRequest.class)
public abstract class AppointmentRequest {

    @JsonProperty("customer_id")
    public abstract long getCustomerId();

    @JsonProperty("date")
    public abstract Date getDate();
}
