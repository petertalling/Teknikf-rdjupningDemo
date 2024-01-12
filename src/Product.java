import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Product {
    public String sku;
    private CurrentState currentState = new CurrentState();
    private List<EventInterface> events = new ArrayList();

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
        switch (event) {
            case ProductShipped productShipped -> apply(productShipped);
            case ProductReceived productReceived -> apply(productReceived);
            case ProductAdjusted productAdjusted -> apply(productAdjusted);
            default -> System.out.println("Event type not supported");
        }
        events.add(event);
    }

    public void apply(ProductShipped event) {
        currentState.quantityInStock -= event.quantity();
    }

    public void apply(ProductReceived event) {
        currentState.quantityInStock += event.quantity();
    }

    public void apply(ProductAdjusted event) {
        currentState.quantityInStock += event.quantity();
    }

    public List<EventInterface> getEvents() {
        return events;
    }

    public int getQuantityInStock() {
        return currentState.quantityInStock;
    }
}
