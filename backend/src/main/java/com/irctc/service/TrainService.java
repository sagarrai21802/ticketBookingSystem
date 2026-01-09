package com.irctc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irctc.model.Station;
import com.irctc.model.Train;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainService {

    private List<Station> stations = new ArrayList<>();
    private List<Train> trains = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadData() {
        try {
            // Load stations
            InputStream stationsStream = new ClassPathResource("data/stations.json").getInputStream();
            stations = objectMapper.readValue(stationsStream, new TypeReference<List<Station>>() {});
            System.out.println("✅ Loaded " + stations.size() + " stations");

            // Load trains
            InputStream trainsStream = new ClassPathResource("data/trains.json").getInputStream();
            trains = objectMapper.readValue(trainsStream, new TypeReference<List<Train>>() {});
            System.out.println("✅ Loaded " + trains.size() + " trains");

        } catch (IOException e) {
            System.err.println("❌ Error loading data: " + e.getMessage());
        }
    }

    public List<Station> getAllStations() {
        return stations;
    }

    public List<Train> getAllTrains() {
        return trains;
    }

    public List<Train> searchTrains(String source, String destination) {
        return trains.stream()
                .filter(t -> t.getSource().equalsIgnoreCase(source) 
                          && t.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    public Station getStationByCode(String code) {
        return stations.stream()
                .filter(s -> s.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }
}
