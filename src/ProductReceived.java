import java.time.LocalDateTime;

public record ProductReceived(String item, int quantity, LocalDateTime dateTime) implements EventInterface {

    public int quantity() {
        return this.quantity;
    }
    public LocalDateTime dateTime() {
        return this.dateTime;
    }
}
