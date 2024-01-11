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
        if (quantity > this.currentState.quantityInStock) {
            System.out.println("Not enough in stock of this product..");
            return false;
        } else {
            this.addEvent(new ProductShipped(this.sku, quantity, LocalDateTime.now()));
            return true;
        }
    }

    public void receiveProduct(int quantity) {
        this.addEvent(new ProductReceived(this.sku, quantity, LocalDateTime.now()));
    }

    public boolean adjustInventory(int quantity, String reason) {
        if (this.currentState.quantityInStock + quantity < 0) {
            System.out.println("Not enough in stock of this product..");
            return false;
        } else {
            this.addEvent(new ProductAdjusted(this.sku, quantity, reason, LocalDateTime.now()));
            return true;
        }
    }

    public void addEvent(EventInterface event) {
        switch (event) {
            case ProductShipped productShipped:
                this.apply(productShipped);
                break;
            case ProductReceived productReceived:
                this.apply(productReceived);
                break;
            case ProductAdjusted productAdjusted:
                this.apply(productAdjusted);
                break;
            default:
                System.out.println("Event type not supported");
        }

        this.events.add(event);
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
        return this.events;
    }

    public int getQuantityInStock() {
        return this.currentState.quantityInStock;
    }
}
