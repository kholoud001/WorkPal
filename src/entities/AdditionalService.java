package entities;

public class AdditionalService {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private int userId;

    public AdditionalService() {}

    public AdditionalService(int id, String name, int quantity, double price, int userId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AdditionalService [id=" + id + ", name=" + name + ", quantity=" + quantity + ", price=" + price + ", userId=" + userId + "]";
    }
}
