import java.util.HashMap;
import java.util.List;

public class ProductRepository {
    private HashMap<String, List<EventInterface>> inMemoryStreams = new HashMap();
    //private HashMap<String, ProductInfo> snapshots = new HashMap();

    public ProductRepository() {
    }

    public Product get(String sku) {
        Product product = new Product(sku);
        if (inMemoryStreams.containsKey(sku)) {
            inMemoryStreams.get(sku).forEach(product::addEvent);
        }
        return product;
    }

    public void save(Product product) {
        inMemoryStreams.put(product.sku, product.getEvents());
    }
}
