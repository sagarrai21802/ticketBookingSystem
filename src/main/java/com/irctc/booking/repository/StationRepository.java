package com.irctc.booking.repository;

import com.irctc.booking.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Station entity
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    
    Optional<Station> findByStationCode(String stationCode);
    
    Optional<Station> findByStationCodeIgnoreCase(String stationCode);
    
    List<Station> findByStateIgnoreCase(String state);
    
    List<Station> findByCityContainingIgnoreCase(String city);
    
    List<Station> findByStationNameContainingIgnoreCase(String name);
    
    List<Station> findByIsActiveTrue();
    
    @Query("SELECT s FROM Station s WHERE " +
           "LOWER(s.stationCode) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.stationName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.city) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Station> searchStations(@Param("query") String query);
}
