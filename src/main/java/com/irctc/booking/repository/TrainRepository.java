package com.irctc.booking.repository;

import com.irctc.booking.model.Train;
import com.irctc.booking.model.TrainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Train entity
 */
@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    
    Optional<Train> findByTrainNumber(String trainNumber);
    
    List<Train> findByTrainType(TrainType trainType);
    
    List<Train> findByIsActiveTrue();
    
    List<Train> findByTrainNameContainingIgnoreCase(String name);
    
    @Query("SELECT t FROM Train t WHERE " +
           "t.sourceStation.stationCode = :sourceCode AND " +
           "t.destinationStation.stationCode = :destCode AND " +
           "t.isActive = true")
    List<Train> findBySourceAndDestination(
            @Param("sourceCode") String sourceCode,
            @Param("destCode") String destCode);
    
    @Query("SELECT t FROM Train t WHERE " +
           "(LOWER(t.trainNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(t.trainName) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "t.isActive = true")
    List<Train> searchTrains(@Param("query") String query);
    
    @Query("SELECT DISTINCT t FROM Train t " +
           "JOIN TrainSchedule ts1 ON ts1.train = t " +
           "JOIN TrainSchedule ts2 ON ts2.train = t " +
           "WHERE ts1.station.stationCode = :sourceCode " +
           "AND ts2.station.stationCode = :destCode " +
           "AND ts1.stopNumber < ts2.stopNumber " +
           "AND t.isActive = true")
    List<Train> findTrainsBetweenStations(
            @Param("sourceCode") String sourceCode, 
            @Param("destCode") String destCode);
}
