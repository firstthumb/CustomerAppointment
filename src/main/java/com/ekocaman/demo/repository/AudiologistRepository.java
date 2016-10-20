package com.ekocaman.demo.repository;

import com.ekocaman.demo.model.Audiologist;
import com.ekocaman.demo.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudiologistRepository extends PagingAndSortingRepository<Audiologist, Long> {

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);
}
