import java.util.HashMap;
import java.util.List;

public class ProductRepository {
    private final HashMap<String, List<EventInterface>> eventStore = new HashMap<>();
    private final SnapshotRepository snapshotsRepository = new SnapshotRepository();

    public ProductRepository() {
    }

    public Product get(String sku) {
        Product product = new Product(sku);
        //Add all events to product if any
        if (eventStore.containsKey(sku)) {
            product.setEvents(eventStore.get(sku));
        } else {
            return product;
        }
        //Set product current state to latest snapshot if exists
        if (snapshotsRepository.snapshots.containsKey(sku)) {
            List<CurrentState> currentStates = snapshotsRepository.snapshots.get(sku);
            CurrentState lastCurrentState = currentStates.get(currentStates.size() - 1).clone();
            product.setCurrentState(lastCurrentState);

            //Apply all events after snapshot sequence number to product
            List<EventInterface> eventsToAddToProduct = eventStore.get(sku).stream().
                    skip(lastCurrentState.sequenceNumber).toList();
            eventsToAddToProduct.forEach(product::applyEvent);

        } else {
            eventStore.get(sku).forEach(product::applyEvent);
        }
        return product;
    }
    public void save(Product product) {
        eventStore.put(product.sku, product.getEvents());
        if (product.currentState.sequenceNumber - snapshotsRepository.getCurrentState(product.sku).sequenceNumber >= 5) {
            snapshotsRepository.createSnapshot(product);
        }
    }
    public List<EventInterface> eventHistory(String sku) {
        return eventStore.get(sku);
    }
}
