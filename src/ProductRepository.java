import java.util.HashMap;
import java.util.List;

public class ProductRepository {
    private HashMap<String, List<EventInterface>> inMemoryStreams = new HashMap();
    private SnapshotRepository snapshotsRepository = new SnapshotRepository();

    public ProductRepository() {
    }

    public Product get(String sku) {
        Product product = new Product(sku);
        //Add all events to product if any
        if (inMemoryStreams.containsKey(sku)) {
            product.setEvents(inMemoryStreams.get(sku));
        }else {
            return product;
        }
        //Set product current state to latest snapshot if exists
        if (snapshotsRepository.snapshots.containsKey(sku)) {
            List<CurrentState> currentStates = snapshotsRepository.snapshots.get(sku);
            CurrentState lastCurrentState = currentStates.get(currentStates.size() - 1).clone();
            product.setCurrentState(lastCurrentState);

            //Read all events after product sequence number
            List<EventInterface> eventsToAddToProduct = inMemoryStreams.get(sku).stream().
                    skip(lastCurrentState.sequenceNumber).toList();
            eventsToAddToProduct.forEach(product::applyEvent);

        } else {
            inMemoryStreams.get(sku).forEach(product::applyEvent);
        }
        return product;
    }

    public void save(Product product) {
        inMemoryStreams.put(product.sku, product.getEvents());
        if (product.currentState.sequenceNumber - snapshotsRepository.getCurrentState(product.sku).sequenceNumber >= 5){

        }
    }

    public List<EventInterface> eventHistory(String sku) {
        return inMemoryStreams.get(sku);
    }
}
