package com.irctc.booking.repository;

import com.irctc.booking.model.Train;
import com.irctc.booking.model.TrainSchedule;
import com.irctc.booking.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for TrainSchedule entity
 */
@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {
    
    List<TrainSchedule> findByTrainOrderByStopNumberAsc(Train train);
    
    List<TrainSchedule> findByTrainTrainNumberOrderByStopNumberAsc(String trainNumber);
    
    List<TrainSchedule> findByStation(Station station);
    
    Optional<TrainSchedule> findByTrainAndStation(Train train, Station station);
    
    @Query("SELECT ts FROM TrainSchedule ts WHERE " +
           "ts.train.trainNumber = :trainNumber AND " +
           "ts.station.stationCode = :stationCode")
    Optional<TrainSchedule> findByTrainNumberAndStationCode(
            @Param("trainNumber") String trainNumber,
            @Param("stationCode") String stationCode);
    
    @Query("SELECT ts FROM TrainSchedule ts WHERE " +
           "ts.station.stationCode = :stationCode AND " +
           "ts.isActive = true " +
           "ORDER BY ts.departureTime")
    List<TrainSchedule> findTrainsPassingStation(@Param("stationCode") String stationCode);
    
    @Query("SELECT ts FROM TrainSchedule ts WHERE " +
           "ts.train = :train " +
           "ORDER BY ts.stopNumber")
    List<TrainSchedule> getTrainRoute(@Param("train") Train train);
}
