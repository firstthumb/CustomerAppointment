package com.ekocaman.demo.repository;

import com.ekocaman.demo.model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

}
