package com.irctc.booking.service;

import com.irctc.booking.dto.request.BookingRequest;
import com.irctc.booking.dto.request.PassengerRequest;
import com.irctc.booking.dto.response.BookingResponse;
import com.irctc.booking.dto.response.PassengerResponse;
import com.irctc.booking.exception.BookingException;
import com.irctc.booking.exception.ResourceNotFoundException;
import com.irctc.booking.model.*;
import com.irctc.booking.repository.*;
import com.irctc.booking.util.PnrGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for booking management
 */
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TrainScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new booking
     */
    @Transactional
    public BookingResponse createBooking(BookingRequest request, String username) {
        // Get user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Validate train
        Train train = trainRepository.findByTrainNumber(request.getTrainNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Train", "trainNumber", request.getTrainNumber()));

        // Validate stations
        Station source = stationRepository.findByStationCodeIgnoreCase(request.getSourceStationCode())
                .orElseThrow(() -> new ResourceNotFoundException("Station", "code", request.getSourceStationCode()));
        Station dest = stationRepository.findByStationCodeIgnoreCase(request.getDestinationStationCode())
                .orElseThrow(() -> new ResourceNotFoundException("Station", "code", request.getDestinationStationCode()));

        // Validate journey date
        if (request.getJourneyDate().isBefore(LocalDate.now())) {
            throw new BookingException("Journey date cannot be in the past");
        }

        // Calculate distance and fare
        int distance = calculateDistance(train.getTrainNumber(), source.getStationCode(), dest.getStationCode());
        BigDecimal baseFare = calculateFare(train, request.getCoachClass(), distance, request.getPassengers().size());

        // Generate PNR
        String pnr;
        do {
            pnr = PnrGenerator.generatePnr();
        } while (bookingRepository.existsByPnrNumber(pnr));

        // Calculate total fare with taxes
        BigDecimal reservationCharge = BigDecimal.valueOf(40 * request.getPassengers().size());
        BigDecimal serviceTax = baseFare.multiply(BigDecimal.valueOf(0.05)).setScale(0, RoundingMode.HALF_UP);
        BigDecimal totalFare = baseFare.add(reservationCharge).add(serviceTax);

        // Create booking
        Booking booking = Booking.builder()
                .pnrNumber(pnr)
                .user(user)
                .train(train)
                .sourceStation(source)
                .destinationStation(dest)
                .journeyDate(request.getJourneyDate())
                .coachClass(request.getCoachClass())
                .quotaType(request.getQuotaType())
                .status(BookingStatus.PENDING)
                .baseFare(baseFare)
                .reservationCharge(reservationCharge)
                .serviceTax(serviceTax)
                .totalFare(totalFare)
                .distanceKm(distance)
                .contactEmail(request.getContactEmail())
                .contactMobile(request.getContactMobile())
                .passengers(new ArrayList<>())
                .build();

        // Add passengers
        for (PassengerRequest pReq : request.getPassengers()) {
            Passenger passenger = Passenger.builder()
                    .booking(booking)
                    .name(pReq.getName())
                    .age(pReq.getAge())
                    .gender(pReq.getGender())
                    .berthPreference(pReq.getBerthPreference())
                    .foodPreference(pReq.getFoodPreference())
                    .idProofType(pReq.getIdProofType())
                    .idProofNumber(pReq.getIdProofNumber())
                    .concessionCategory(pReq.getConcessionCategory())
                    .nationality(pReq.getNationality())
                    .status(PassengerStatus.CONFIRMED)
                    .build();
            booking.getPassengers().add(passenger);
        }

        Booking saved = bookingRepository.save(booking);

        return mapToBookingResponse(saved);
    }

    /**
     * Get booking by PNR
     */
    public BookingResponse getBookingByPnr(String pnr) {
        Booking booking = bookingRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "PNR", pnr));
        return mapToBookingResponse(booking);
    }

    /**
     * Get user's bookings
     */
    public List<BookingResponse> getUserBookings(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        List<Booking> bookings = bookingRepository.findByUserOrderByBookedAtDesc(user);
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get upcoming bookings
     */
    public List<BookingResponse> getUpcomingBookings(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        List<Booking> bookings = bookingRepository.findUpcomingBookingsByUser(user, LocalDate.now());
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    /**
     * Cancel booking
     */
    @Transactional
    public BookingResponse cancelBooking(String pnr, String username) {
        Booking booking = bookingRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "PNR", pnr));

        // Verify ownership
        if (!booking.getUser().getUsername().equals(username)) {
            throw new BookingException("You can only cancel your own bookings");
        }

        // Check if already cancelled
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingException("Booking is already cancelled");
        }

        // Check if journey date has passed
        if (booking.getJourneyDate().isBefore(LocalDate.now())) {
            throw new BookingException("Cannot cancel past bookings");
        }

        // Calculate refund (simplified logic)
        BigDecimal refund = calculateRefund(booking);

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        booking.setRefundAmount(refund);

        // Update passenger status
        for (Passenger passenger : booking.getPassengers()) {
            passenger.setStatus(PassengerStatus.CANCELLED);
        }

        Booking saved = bookingRepository.save(booking);
        return mapToBookingResponse(saved);
    }

    /**
     * Confirm booking after payment
     */
    @Transactional
    public BookingResponse confirmBooking(String pnr, String transactionId) {
        Booking booking = bookingRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "PNR", pnr));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BookingException("Only pending bookings can be confirmed");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTransactionId(transactionId);

        // Allocate seats (simplified)
        int seatNumber = 1;
        String coachNumber = booking.getCoachClass().getCode() + "1";
        for (Passenger passenger : booking.getPassengers()) {
            passenger.setCoachNumber(coachNumber);
            passenger.setSeatNumber(seatNumber++);
            passenger.setAllocatedBerth(allocateBerth(passenger.getBerthPreference(), seatNumber));
        }

        Booking saved = bookingRepository.save(booking);
        return mapToBookingResponse(saved);
    }

    // Helper methods
    private int calculateDistance(String trainNumber, String source, String dest) {
        var sourceSchedule = scheduleRepository.findByTrainNumberAndStationCode(trainNumber, source);
        var destSchedule = scheduleRepository.findByTrainNumberAndStationCode(trainNumber, dest);
        
        if (sourceSchedule.isPresent() && destSchedule.isPresent()) {
            return destSchedule.get().getDistanceFromOrigin() - sourceSchedule.get().getDistanceFromOrigin();
        }
        return 500; // Default distance
    }

    private BigDecimal calculateFare(Train train, CoachClass coachClass, int distance, int passengers) {
        BigDecimal rate = switch (coachClass) {
            case FIRST_AC -> train.getFareRate1A();
            case SECOND_AC -> train.getFareRate2A();
            case THIRD_AC -> train.getFareRate3A();
            case SLEEPER -> train.getFareRateSL();
            case CHAIR_CAR -> train.getFareRateCC();
            case SECOND_SITTING -> train.getFareRate2S();
            default -> BigDecimal.valueOf(1.0);
        };
        return rate.multiply(BigDecimal.valueOf(distance * passengers));
    }

    private BigDecimal calculateRefund(Booking booking) {
        long daysToJourney = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), booking.getJourneyDate());
        
        BigDecimal refundPercent;
        if (daysToJourney > 7) {
            refundPercent = BigDecimal.valueOf(0.95); // 5% cancellation charge
        } else if (daysToJourney > 2) {
            refundPercent = BigDecimal.valueOf(0.75); // 25% cancellation charge
        } else if (daysToJourney > 0) {
            refundPercent = BigDecimal.valueOf(0.50); // 50% cancellation charge
        } else {
            refundPercent = BigDecimal.ZERO; // No refund
        }
        
        return booking.getTotalFare().multiply(refundPercent).setScale(0, RoundingMode.HALF_UP);
    }

    private BerthType allocateBerth(BerthPreference preference, int seatNumber) {
        return switch (preference) {
            case LOWER -> BerthType.LOWER;
            case MIDDLE -> BerthType.MIDDLE;
            case UPPER -> BerthType.UPPER;
            case SIDE_LOWER -> BerthType.SIDE_LOWER;
            case SIDE_UPPER -> BerthType.SIDE_UPPER;
            default -> seatNumber % 3 == 1 ? BerthType.LOWER : 
                       seatNumber % 3 == 2 ? BerthType.MIDDLE : BerthType.UPPER;
        };
    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        List<PassengerResponse> passengers = booking.getPassengers().stream()
                .map(p -> PassengerResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .age(p.getAge())
                        .gender(p.getGender())
                        .berthPreference(p.getBerthPreference())
                        .coachNumber(p.getCoachNumber())
                        .seatNumber(p.getSeatNumber())
                        .allocatedBerth(p.getAllocatedBerth())
                        .status(p.getStatus())
                        .waitlistNumber(p.getWaitlistNumber())
                        .racNumber(p.getRacNumber())
                        .foodPreference(p.getFoodPreference())
                        .concessionCategory(p.getConcessionCategory())
                        .build())
                .collect(Collectors.toList());

        return BookingResponse.builder()
                .pnrNumber(booking.getPnrNumber())
                .status(booking.getStatus())
                .trainNumber(booking.getTrain().getTrainNumber())
                .trainName(booking.getTrain().getTrainName())
                .trainType(booking.getTrain().getTrainType().getDisplayName())
                .sourceStation(booking.getSourceStation().getStationCode())
                .sourceStationName(booking.getSourceStation().getStationName())
                .destinationStation(booking.getDestinationStation().getStationCode())
                .destinationStationName(booking.getDestinationStation().getStationName())
                .journeyDate(booking.getJourneyDate())
                .coachClass(booking.getCoachClass())
                .quotaType(booking.getQuotaType())
                .passengers(passengers)
                .baseFare(booking.getBaseFare())
                .reservationCharge(booking.getReservationCharge())
                .serviceTax(booking.getServiceTax())
                .cateringCharge(booking.getCateringCharge())
                .totalFare(booking.getTotalFare())
                .distanceKm(booking.getDistanceKm())
                .contactEmail(booking.getContactEmail())
                .contactMobile(booking.getContactMobile())
                .bookedAt(booking.getBookedAt())
                .cancelledAt(booking.getCancelledAt())
                .refundAmount(booking.getRefundAmount())
                .transactionId(booking.getTransactionId())
                .build();
    }
}
