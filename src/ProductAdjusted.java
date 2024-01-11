import java.time.LocalDateTime;

public record ProductAdjusted(String item, int quantity, String reason,
                              LocalDateTime dateTime) implements EventInterface {
    public ProductAdjusted(String item, int quantity, String reason, LocalDateTime dateTime) {
        this.item = item;
        this.quantity = quantity;
        this.reason = reason;
        this.dateTime = dateTime;
    }

    public String item() {
        return this.item;
    }

    public int quantity() {
        return this.quantity;
    }

    public String reason() {
        return this.reason;
    }

    public LocalDateTime dateTime() {
        return this.dateTime;
    }
}
