import java.time.LocalDateTime;

public record ProductShipped(String item, int quantity, LocalDateTime dateTime) implements EventInterface {
    public ProductShipped(String item, int quantity, LocalDateTime dateTime) {
        this.item = item;
        this.quantity = quantity;
        this.dateTime = dateTime;
    }

    public String item() {
        return this.item;
    }

    public int quantity() {
        return this.quantity;
    }

    public LocalDateTime dateTime() {
        return this.dateTime;
    }
}
