package com.irctc.booking.service;

import com.irctc.booking.dto.response.TrainSearchResponse;
import com.irctc.booking.exception.ResourceNotFoundException;
import com.irctc.booking.model.*;
import com.irctc.booking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Service for train management and search
 */
@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainScheduleRepository scheduleRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Get all active trains
     */
    public List<Train> getAllTrains() {
        return trainRepository.findByIsActiveTrue();
    }

    /**
     * Get train by ID
     */
    public Train getTrainById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train", "id", id));
    }

    /**
     * Get train by train number
     */
    public Train getTrainByNumber(String trainNumber) {
        return trainRepository.findByTrainNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train", "trainNumber", trainNumber));
    }

    /**
     * Search trains between stations on a date
     */
    public List<TrainSearchResponse> searchTrains(String sourceCode, String destCode, LocalDate journeyDate) {
        // Validate stations exist
        Station source = stationRepository.findByStationCodeIgnoreCase(sourceCode)
                .orElseThrow(() -> new ResourceNotFoundException("Station", "code", sourceCode));
        Station dest = stationRepository.findByStationCodeIgnoreCase(destCode)
                .orElseThrow(() -> new ResourceNotFoundException("Station", "code", destCode));

        // Get day of week (1=Sunday, 2=Monday, ... 7=Saturday)
        int dayOfWeek = journeyDate.getDayOfWeek().getValue() % 7 + 1;

        // Find trains between stations
        List<Train> trains = trainRepository.findTrainsBetweenStations(sourceCode.toUpperCase(), destCode.toUpperCase());

        List<TrainSearchResponse> results = new ArrayList<>();

        for (Train train : trains) {
            // Check if train runs on this day
            if (train.getRunningDays() != null && !train.getRunningDays().contains(dayOfWeek)) {
                continue;
            }

            // Get schedule for source and destination
            Optional<TrainSchedule> sourceScheduleOpt = scheduleRepository.findByTrainNumberAndStationCode(
                    train.getTrainNumber(), sourceCode.toUpperCase());
            Optional<TrainSchedule> destScheduleOpt = scheduleRepository.findByTrainNumberAndStationCode(
                    train.getTrainNumber(), destCode.toUpperCase());

            if (sourceScheduleOpt.isEmpty() || destScheduleOpt.isEmpty()) {
                continue;
            }

            TrainSchedule sourceSchedule = sourceScheduleOpt.get();
            TrainSchedule destSchedule = destScheduleOpt.get();

            // Calculate distance and duration
            int distance = destSchedule.getDistanceFromOrigin() - sourceSchedule.getDistanceFromOrigin();
            String duration = calculateDuration(sourceSchedule.getDepartureTime(), destSchedule.getArrivalTime(),
                    destSchedule.getDayOffset() - sourceSchedule.getDayOffset());

            // Build seat availability map
            Map<String, TrainSearchResponse.SeatAvailability> seatAvailability = buildSeatAvailability(
                    train, journeyDate, distance);

            // Build running days text
            String runningDaysText = formatRunningDays(train.getRunningDays());

            TrainSearchResponse response = TrainSearchResponse.builder()
                    .trainNumber(train.getTrainNumber())
                    .trainName(train.getTrainName())
                    .trainType(train.getTrainType().getDisplayName())
                    .sourceStation(sourceCode.toUpperCase())
                    .sourceStationName(source.getStationName())
                    .departureTime(sourceSchedule.getDepartureTime())
                    .destinationStation(destCode.toUpperCase())
                    .destinationStationName(dest.getStationName())
                    .arrivalTime(destSchedule.getArrivalTime())
                    .duration(duration)
                    .distanceKm(distance)
                    .runningDays(train.getRunningDays())
                    .runningDaysText(runningDaysText)
                    .seatAvailability(seatAvailability)
                    .hasPantry(train.getHasPantry())
                    .build();

            results.add(response);
        }

        return results;
    }

    /**
     * Get train route (all stops)
     */
    public List<TrainSchedule> getTrainRoute(String trainNumber) {
        Train train = getTrainByNumber(trainNumber);
        return scheduleRepository.getTrainRoute(train);
    }

    /**
     * Calculate seat availability for each class
     */
    private Map<String, TrainSearchResponse.SeatAvailability> buildSeatAvailability(
            Train train, LocalDate journeyDate, int distance) {
        
        Map<String, TrainSearchResponse.SeatAvailability> availability = new HashMap<>();

        // 1A - First AC
        if (train.getTotalSeats1A() > 0) {
            int booked = getBookedSeats(train.getTrainNumber(), journeyDate, CoachClass.FIRST_AC);
            int available = train.getTotalSeats1A() - booked;
            BigDecimal fare = train.getFareRate1A().multiply(BigDecimal.valueOf(distance));
            
            availability.put("1A", buildAvailability(CoachClass.FIRST_AC, available, fare, train.getTotalSeats1A()));
        }

        // 2A - Second AC
        if (train.getTotalSeats2A() > 0) {
            int booked = getBookedSeats(train.getTrainNumber(), journeyDate, CoachClass.SECOND_AC);
            int available = train.getTotalSeats2A() - booked;
            BigDecimal fare = train.getFareRate2A().multiply(BigDecimal.valueOf(distance));
            
            availability.put("2A", buildAvailability(CoachClass.SECOND_AC, available, fare, train.getTotalSeats2A()));
        }

        // 3A - Third AC
        if (train.getTotalSeats3A() > 0) {
            int booked = getBookedSeats(train.getTrainNumber(), journeyDate, CoachClass.THIRD_AC);
            int available = train.getTotalSeats3A() - booked;
            BigDecimal fare = train.getFareRate3A().multiply(BigDecimal.valueOf(distance));
            
            availability.put("3A", buildAvailability(CoachClass.THIRD_AC, available, fare, train.getTotalSeats3A()));
        }

        // SL - Sleeper
        if (train.getTotalSeatsSL() > 0) {
            int booked = getBookedSeats(train.getTrainNumber(), journeyDate, CoachClass.SLEEPER);
            int available = train.getTotalSeatsSL() - booked;
            BigDecimal fare = train.getFareRateSL().multiply(BigDecimal.valueOf(distance));
            
            availability.put("SL", buildAvailability(CoachClass.SLEEPER, available, fare, train.getTotalSeatsSL()));
        }

        // CC - Chair Car
        if (train.getTotalSeatsCC() > 0) {
            int booked = getBookedSeats(train.getTrainNumber(), journeyDate, CoachClass.CHAIR_CAR);
            int available = train.getTotalSeatsCC() - booked;
            BigDecimal fare = train.getFareRateCC().multiply(BigDecimal.valueOf(distance));
            
            availability.put("CC", buildAvailability(CoachClass.CHAIR_CAR, available, fare, train.getTotalSeatsCC()));
        }

        // 2S - Second Sitting
        if (train.getTotalSeats2S() > 0) {
            int booked = getBookedSeats(train.getTrainNumber(), journeyDate, CoachClass.SECOND_SITTING);
            int available = train.getTotalSeats2S() - booked;
            BigDecimal fare = train.getFareRate2S().multiply(BigDecimal.valueOf(distance));
            
            availability.put("2S", buildAvailability(CoachClass.SECOND_SITTING, available, fare, train.getTotalSeats2S()));
        }

        return availability;
    }

    private TrainSearchResponse.SeatAvailability buildAvailability(CoachClass coachClass, int available, 
                                                                    BigDecimal fare, int total) {
        String status;
        int waitlist = 0;
        
        if (available > 10) {
            status = "AVAILABLE";
        } else if (available > 0) {
            status = "AVAILABLE";
        } else if (available > -10) {
            status = "RAC";
            waitlist = Math.abs(available);
        } else {
            status = "WAITING LIST";
            waitlist = Math.abs(available);
        }

        return TrainSearchResponse.SeatAvailability.builder()
                .coachClass(coachClass.getCode())
                .className(coachClass.getDisplayName())
                .availableSeats(Math.max(available, 0))
                .fare(fare.setScale(0, java.math.RoundingMode.HALF_UP))
                .status(status)
                .waitlistCount(waitlist)
                .build();
    }

    private int getBookedSeats(String trainNumber, LocalDate date, CoachClass coachClass) {
        // In real implementation, count confirmed passengers
        // For now, return random availability
        return new Random().nextInt(50);
    }

    private String calculateDuration(LocalTime departure, LocalTime arrival, int dayOffset) {
        long minutes = ChronoUnit.MINUTES.between(departure, arrival);
        if (dayOffset > 0 || minutes < 0) {
            minutes += dayOffset * 24 * 60;
            if (minutes < 0) minutes += 24 * 60;
        }
        long hours = minutes / 60;
        long mins = minutes % 60;
        return String.format("%dh %02dm", hours, mins);
    }

    private String formatRunningDays(List<Integer> days) {
        if (days == null || days.isEmpty()) return "Daily";
        if (days.size() == 7) return "Daily";
        
        String[] dayNames = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        StringBuilder sb = new StringBuilder();
        for (int day : days) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(dayNames[day]);
        }
        return sb.toString();
    }

    /**
     * Create a new train (Admin only)
     */
    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }
}
