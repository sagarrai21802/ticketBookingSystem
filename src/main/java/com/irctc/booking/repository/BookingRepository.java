package com.irctc.booking.repository;

import com.irctc.booking.model.Booking;
import com.irctc.booking.model.BookingStatus;
import com.irctc.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Booking entity
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    Optional<Booking> findByPnrNumber(String pnrNumber);
    
    List<Booking> findByUser(User user);
    
    List<Booking> findByUserOrderByBookedAtDesc(User user);
    
    List<Booking> findByStatus(BookingStatus status);
    
    List<Booking> findByJourneyDate(LocalDate journeyDate);
    
    List<Booking> findByTrainTrainNumber(String trainNumber);
    
    @Query("SELECT b FROM Booking b WHERE " +
           "b.user = :user AND " +
           "b.journeyDate >= :date " +
           "ORDER BY b.journeyDate ASC")
    List<Booking> findUpcomingBookingsByUser(
            @Param("user") User user, 
            @Param("date") LocalDate date);
    
    @Query("SELECT b FROM Booking b WHERE " +
           "b.user = :user AND " +
           "b.journeyDate < :date " +
           "ORDER BY b.journeyDate DESC")
    List<Booking> findPastBookingsByUser(
            @Param("user") User user, 
            @Param("date") LocalDate date);
    
    @Query("SELECT b FROM Booking b WHERE " +
           "b.train.trainNumber = :trainNumber AND " +
           "b.journeyDate = :journeyDate AND " +
           "b.status NOT IN ('CANCELLED')")
    List<Booking> findActiveBookingsForTrainOnDate(
            @Param("trainNumber") String trainNumber, 
            @Param("journeyDate") LocalDate journeyDate);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE " +
           "b.train.trainNumber = :trainNumber AND " +
           "b.journeyDate = :journeyDate AND " +
           "b.coachClass = :coachClass AND " +
           "b.status = 'CONFIRMED'")
    Long countConfirmedBookings(
            @Param("trainNumber") String trainNumber,
            @Param("journeyDate") LocalDate journeyDate,
            @Param("coachClass") String coachClass);
    
    boolean existsByPnrNumber(String pnrNumber);
}
