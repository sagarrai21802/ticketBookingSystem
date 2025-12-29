package com.irctc.booking.repository;

import com.irctc.booking.model.Passenger;
import com.irctc.booking.model.PassengerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Passenger entity
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    
    List<Passenger> findByBookingId(Long bookingId);
    
    List<Passenger> findByBookingPnrNumber(String pnrNumber);
    
    List<Passenger> findByStatus(PassengerStatus status);
    
    List<Passenger> findByBookingIdAndStatus(Long bookingId, PassengerStatus status);
}
