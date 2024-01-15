import java.time.LocalDateTime;

public record ProductAdjusted(String item, int quantity, String reason, LocalDateTime dateTime) implements EventInterface {

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
