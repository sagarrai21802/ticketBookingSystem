package com.irctc.booking.controller;

import com.irctc.booking.dto.response.ApiResponse;
import com.irctc.booking.model.Station;
import com.irctc.booking.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for station management
 */
@RestController
@RequestMapping("/api/stations")
@Tag(name = "Stations", description = "Railway station APIs")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping
    @Operation(summary = "Get all stations", description = "Get list of all active railway stations")
    public ResponseEntity<ApiResponse<List<Station>>> getAllStations() {
        List<Station> stations = stationService.getAllStations();
        return ResponseEntity.ok(ApiResponse.success(stations));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get station by ID")
    public ResponseEntity<ApiResponse<Station>> getStationById(@PathVariable Long id) {
        Station station = stationService.getStationById(id);
        return ResponseEntity.ok(ApiResponse.success(station));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get station by code", description = "Get station by station code like NDLS, BCT, etc.")
    public ResponseEntity<ApiResponse<Station>> getStationByCode(@PathVariable String code) {
        Station station = stationService.getStationByCode(code);
        return ResponseEntity.ok(ApiResponse.success(station));
    }

    @GetMapping("/search")
    @Operation(summary = "Search stations", description = "Search stations by name, code or city")
    public ResponseEntity<ApiResponse<List<Station>>> searchStations(@RequestParam String query) {
        List<Station> stations = stationService.searchStations(query);
        return ResponseEntity.ok(ApiResponse.success(stations));
    }

    @GetMapping("/state/{state}")
    @Operation(summary = "Get stations by state")
    public ResponseEntity<ApiResponse<List<Station>>> getStationsByState(@PathVariable String state) {
        List<Station> stations = stationService.getStationsByState(state);
        return ResponseEntity.ok(ApiResponse.success(stations));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create station (Admin only)")
    public ResponseEntity<ApiResponse<Station>> createStation(@RequestBody Station station) {
        Station created = stationService.createStation(station);
        return ResponseEntity.ok(ApiResponse.success("Station created successfully", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update station (Admin only)")
    public ResponseEntity<ApiResponse<Station>> updateStation(@PathVariable Long id, @RequestBody Station station) {
        Station updated = stationService.updateStation(id, station);
        return ResponseEntity.ok(ApiResponse.success("Station updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate station (Admin only)")
    public ResponseEntity<ApiResponse<Void>> deactivateStation(@PathVariable Long id) {
        stationService.deactivateStation(id);
        return ResponseEntity.ok(ApiResponse.success("Station deactivated successfully", null));
    }
}
