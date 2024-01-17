import java.util.HashMap;
import java.util.List;

public class ProductRepository {
    private final HashMap<String, List<EventInterface>> eventStore = new HashMap<>();

    public ProductRepository() {
    }

    public Product get(String sku) {
        Product product = new Product(sku);
        if (eventStore.containsKey(sku)) {
            eventStore.get(sku).forEach(product::addEvent);
        }
        return product;
    }
    public void save(Product product) {
        this.eventStore.put(product.sku, product.getEvents());
    }
    public List<EventInterface> eventHistory(String sku) {
        return eventStore.get(sku);
    }
}
