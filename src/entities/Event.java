package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int id;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private String location;  // Can be the name of the space or a physical address
    private String type;  // Example: "Meeting", "Workshop"
    private double price;  // Event-specific price
    private int spaceId;  // ID of the space related to this event

    // Constructor
    public Event(int id, String title, LocalDate date, LocalTime time, String location, String type, double price, int spaceId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.type = type;
        this.price = price;
        this.spaceId = spaceId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", spaceId=" + spaceId +
                '}';
    }
}
