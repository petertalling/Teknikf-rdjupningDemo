import java.time.LocalDateTime;

public record ProductReceived(String item, int quantity, LocalDateTime dateTime) implements EventInterface {
    public ProductReceived(String item, int quantity, LocalDateTime dateTime) {
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
