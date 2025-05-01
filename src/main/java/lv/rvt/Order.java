package lv.rvt;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String username;                
    private List<String> items;            
    private double totalPrice;             
    private String deliveryMethod;         
    private LocalDateTime orderTime;       

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
    @Override
    public String toString() {
        String itemsList = String.join(", ", items);
        return "Pasūtījums no lietotāja: " + username + "\n" +
           "Pasūtītās picas: " + itemsList + "\n" +
           "Kopējā cena: " + totalPrice + "€\n" +
           "Piegādes metode: " + deliveryMethod + "\n" +
           "Pasūtījuma laiks: " + orderTime;
    }
}
