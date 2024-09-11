package entities;

import Enums.TypeSpace;

public class Space {
    private int id;
    private String name;
    private String location;
    private TypeSpace type;
    private int size;
    private boolean availability;
    private String equipment;
    private String policy;
    private int userId;
    private double price;

    // Constructor
    public Space(int id, String name, String location, TypeSpace type, int size, boolean availability, String equipment, String policy, int userId, double price) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.size = size;
        this.availability = availability;
        this.equipment = equipment;
        this.policy = policy;
        this.userId = userId;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public TypeSpace getType() {
        return type;
    }

    public void setType(TypeSpace type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Space{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", type=" + type +
                ", size=" + size +
                ", availability=" + availability +
                ", equipment='" + equipment + '\'' +
                ", policy='" + policy + '\'' +
                ", userId=" + userId +
                ", price=" + price +
                '}';
    }
}
