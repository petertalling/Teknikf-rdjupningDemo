import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Product {
    public String sku;
    public CurrentState currentState = new CurrentState();
    private final List<EventInterface> events = new ArrayList<>();

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }
    public void setEvents(List<EventInterface> events) {
        this.events.clear();
        this.events.addAll(events);
    }
    public List<EventInterface> getEvents() {
        return events;
    }
    public Product(String sku) {
        this.sku = sku;
    }
    public boolean shipProduct(int quantity) {
        if (quantity > currentState.quantityInStock) {
            System.out.println("Not enough in stock of this product..");
            return false;
        } else {
            addEvent(new ProductShipped(sku, quantity, LocalDateTime.now()));
            return true;
        }
    }
    public void receiveProduct(int quantity) {
        addEvent(new ProductReceived(sku, quantity, LocalDateTime.now()));
    }
    public boolean adjustInventory(int quantity, String reason) {
        if (currentState.quantityInStock + quantity < 0) {
            System.out.println("Not enough in stock of this product..");
            return false;
        } else {
            addEvent(new ProductAdjusted(sku, quantity, reason, LocalDateTime.now()));
            return true;
        }
    }
    public void addEvent(EventInterface event) {
        applyEvent(event);
        events.add(event);
    }
    public void applyEvent(EventInterface event){
        switch (event) {
            case ProductShipped productShipped -> applyShipped(productShipped);
            case ProductReceived productReceived -> applyReceived(productReceived);
            case ProductAdjusted productAdjusted -> applyAdjusted(productAdjusted);
            default -> System.out.println("Event type not supported");
        }
    }
    public void applyShipped(ProductShipped event) {
        currentState.quantityInStock -= event.quantity();
    }
    public void applyReceived(ProductReceived event) {
        currentState.quantityInStock += event.quantity();
    }
    public void applyAdjusted(ProductAdjusted event) {
        currentState.quantityInStock += event.quantity();
    }
    public int getQuantityInStock() {
        return currentState.quantityInStock;
    }
}
