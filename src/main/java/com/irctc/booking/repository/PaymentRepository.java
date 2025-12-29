package com.irctc.booking.repository;

import com.irctc.booking.model.Payment;
import com.irctc.booking.model.PaymentStatus;
import com.irctc.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Payment entity
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByTransactionId(String transactionId);
    
    Optional<Payment> findByBookingId(Long bookingId);
    
    Optional<Payment> findByBookingPnrNumber(String pnrNumber);
    
    List<Payment> findByUser(User user);
    
    List<Payment> findByStatus(PaymentStatus status);
    
    List<Payment> findByUserOrderByInitiatedAtDesc(User user);
    
    boolean existsByTransactionId(String transactionId);
}
