package com.irctc.booking.service;

import com.irctc.booking.exception.ResourceNotFoundException;
import com.irctc.booking.model.Station;
import com.irctc.booking.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for station management
 */
@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    /**
     * Get all active stations
     */
    public List<Station> getAllStations() {
        return stationRepository.findByIsActiveTrue();
    }

    /**
     * Get station by ID
     */
    public Station getStationById(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Station", "id", id));
    }

    /**
     * Get station by code
     */
    public Station getStationByCode(String code) {
        return stationRepository.findByStationCodeIgnoreCase(code)
                .orElseThrow(() -> new ResourceNotFoundException("Station", "code", code));
    }

    /**
     * Search stations by name, code or city
     */
    public List<Station> searchStations(String query) {
        return stationRepository.searchStations(query);
    }

    /**
     * Get stations by state
     */
    public List<Station> getStationsByState(String state) {
        return stationRepository.findByStateIgnoreCase(state);
    }

    /**
     * Create a new station (Admin only)
     */
    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    /**
     * Update station (Admin only)
     */
    public Station updateStation(Long id, Station stationDetails) {
        Station station = getStationById(id);
        
        station.setStationCode(stationDetails.getStationCode());
        station.setStationName(stationDetails.getStationName());
        station.setCity(stationDetails.getCity());
        station.setState(stationDetails.getState());
        station.setZone(stationDetails.getZone());
        station.setDivision(stationDetails.getDivision());
        station.setLatitude(stationDetails.getLatitude());
        station.setLongitude(stationDetails.getLongitude());
        
        return stationRepository.save(station);
    }

    /**
     * Deactivate station (Admin only)
     */
    public void deactivateStation(Long id) {
        Station station = getStationById(id);
        station.setIsActive(false);
        stationRepository.save(station);
    }
}
