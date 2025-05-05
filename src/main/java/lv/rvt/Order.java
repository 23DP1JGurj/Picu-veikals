package lv.rvt;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {
    private String username;                
    private List<String> items;            
    private double totalPrice;             
    private String deliveryMethod;         
    private LocalDateTime orderTime;       

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Order(String username, List<String> items, double totalPrice, String deliveryMethod) {
        this.username = username;
        this.items = items;
        this.totalPrice = totalPrice;
        this.deliveryMethod = deliveryMethod;
        this.orderTime = LocalDateTime.now();
    }

 
    public String getUsername() {
        return username;
    }

    public List<String> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }
    public String toString() {
        String itemsList = String.join(", ", items);
        String formattedTotal = String.format("%.2f€", totalPrice);
        String formattedTime = orderTime.format(TIME_FORMATTER);

        return String.format(
            "Pasūtījums no lietotāja: %s%n" +
            "Pasūtītās picas: %s%n" +
            "Kopējā cena: %s%n" +
            "Piegādes metode: %s%n" +
            "Pasūtījuma laiks: %s",
            username != null ? username : "Viesis",
            itemsList,
            formattedTotal,
            deliveryMethod,
            formattedTime
        );
    }
}