package com.ekocaman.demo.response;

import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableAppointmentResponse.class)
@JsonDeserialize(as = ImmutableAppointmentResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AppointmentResponse {

    @JsonProperty("appointment_id")
    public abstract long getAppointmentId();

    @JsonProperty("date")
    public abstract Date getDate();

    @JsonProperty("reviews")
    public abstract List<ReviewResponse> getReviews();

    public static ImmutableAppointmentResponse withAppointment(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        final ImmutableAppointmentResponse.Builder builder = ImmutableAppointmentResponse.builder()
                .appointmentId(appointment.getId())
                .date(appointment.getDate());


        final List<ReviewResponse> reviews = new ArrayList<>();
        if (appointment.getReviews() != null) {
            for (Review review : appointment.getReviews()) {
                reviews.add(ReviewResponse.withReview(review));
            }
        }
        builder.reviews(reviews);

        return builder.build();
    }
}
