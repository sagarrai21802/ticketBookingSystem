package com.irctc.model;

import java.util.List;

public class Train {
    private String number;
    private String name;
    private String type;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int seatsAvailable;
    private double fare;
    private List<String> runningDays;

    public Train() {}

    // Getters and Setters
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getSeatsAvailable() { return seatsAvailable; }
    public void setSeatsAvailable(int seatsAvailable) { this.seatsAvailable = seatsAvailable; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public List<String> getRunningDays() { return runningDays; }
    public void setRunningDays(List<String> runningDays) { this.runningDays = runningDays; }
}
