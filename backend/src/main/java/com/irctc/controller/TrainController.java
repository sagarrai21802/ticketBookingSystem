package com.irctc.controller;

import com.irctc.model.Station;
import com.irctc.model.Train;
import com.irctc.service.TrainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/trains")
    public List<Train> getAllTrains() {
        return trainService.getAllTrains();
    }

    @GetMapping("/stations")
    public List<Station> getAllStations() {
        return trainService.getAllStations();
    }

    @GetMapping("/trains/search")
    public List<Train> searchTrains(
            @RequestParam String from,
            @RequestParam String to) {
        return trainService.searchTrains(from, to);
    }

    @GetMapping("/stations/{code}")
    public Station getStation(@PathVariable String code) {
        return trainService.getStationByCode(code);
    }
}
