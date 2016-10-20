package com.ekocaman.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableCustomerRequest.class)
@JsonDeserialize(as = ImmutableCustomerRequest.class)
public abstract class CustomerRequest {

    @JsonProperty("first_name")
    public abstract String getFirstName();

    @JsonProperty("last_name")
    public abstract String getLastName();
}
