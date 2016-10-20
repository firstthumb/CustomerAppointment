package com.ekocaman.demo.repository;

import com.ekocaman.demo.model.Appointment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, Long> {

    Appointment findFirstByCustomerIdAndDateAfterOrderByDateAsc(@Param("customerId") long customerId, @Param("date") Date date);

    Appointment findFirstByCustomerIdAndDateBeforeOrderByDateDesc(@Param("customerId") long customerId, @Param("date") Date date);

    List<Appointment> findByAudiologistIdAndDateBetweenOrderByDateAsc(@Param("audiologistId") long audiologistId, @Param("from") Date startDate, @Param("to") Date endDate);
}
