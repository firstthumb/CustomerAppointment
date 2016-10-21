package com.ekocaman.demo.response;

import com.ekocaman.demo.model.Appointment;
import com.ekocaman.demo.model.Rating;
import com.ekocaman.demo.model.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
@JsonSerialize(as = ImmutableReviewResponse.class)
@JsonDeserialize(as = ImmutableReviewResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ReviewResponse {

    @JsonProperty("customer_id")
    public abstract long getCustomerId();

    @JsonProperty("rating")
    public abstract Rating getRating();

    @JsonProperty("date")
    public abstract Date getDate();

    public static ImmutableReviewResponse withReview(Review review) {
        if (review == null) {
            return null;
        }

        return ImmutableReviewResponse.builder()
                .customerId(review.getCustomer().getId())
                .rating(review.getRating())
                .date(review.getReviewDate())
                .build();
    }
}

