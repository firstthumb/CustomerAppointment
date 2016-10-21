package com.ekocaman.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableAudiologistRequest.class)
@JsonDeserialize(as = ImmutableAudiologistRequest.class)
public abstract class AudiologistRequest {

    @JsonProperty("first_name")
    public abstract String getFirstName();

    @JsonProperty("last_name")
    public abstract String getLastName();
}
