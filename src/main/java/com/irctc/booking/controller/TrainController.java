package com.irctc.booking.controller;

import com.irctc.booking.dto.response.ApiResponse;
import com.irctc.booking.dto.response.TrainSearchResponse;
import com.irctc.booking.model.Train;
import com.irctc.booking.model.TrainSchedule;
import com.irctc.booking.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for train management and search
 */
@RestController
@RequestMapping("/api/trains")
@Tag(name = "Trains", description = "Train search and management APIs")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @GetMapping
    @Operation(summary = "Get all trains")
    public ResponseEntity<ApiResponse<List<Train>>> getAllTrains() {
        List<Train> trains = trainService.getAllTrains();
        return ResponseEntity.ok(ApiResponse.success(trains));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get train by ID")
    public ResponseEntity<ApiResponse<Train>> getTrainById(@PathVariable Long id) {
        Train train = trainService.getTrainById(id);
        return ResponseEntity.ok(ApiResponse.success(train));
    }

    @GetMapping("/number/{trainNumber}")
    @Operation(summary = "Get train by train number")
    public ResponseEntity<ApiResponse<Train>> getTrainByNumber(@PathVariable String trainNumber) {
        Train train = trainService.getTrainByNumber(trainNumber);
        return ResponseEntity.ok(ApiResponse.success(train));
    }

    @GetMapping("/search")
    @Operation(summary = "Search trains", 
               description = "Search trains between source and destination on a specific date")
    public ResponseEntity<ApiResponse<List<TrainSearchResponse>>> searchTrains(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        List<TrainSearchResponse> results = trainService.searchTrains(from, to, date);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/{trainNumber}/route")
    @Operation(summary = "Get train route", description = "Get all stops for a train")
    public ResponseEntity<ApiResponse<List<TrainSchedule>>> getTrainRoute(@PathVariable String trainNumber) {
        List<TrainSchedule> route = trainService.getTrainRoute(trainNumber);
        return ResponseEntity.ok(ApiResponse.success(route));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create train (Admin only)")
    public ResponseEntity<ApiResponse<Train>> createTrain(@RequestBody Train train) {
        Train created = trainService.createTrain(train);
        return ResponseEntity.ok(ApiResponse.success("Train created successfully", created));
    }
}
