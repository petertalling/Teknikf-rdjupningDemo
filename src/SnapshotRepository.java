import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SnapshotRepository {
    public HashMap<String, List<CurrentState>> snapshots = new HashMap();

    public CurrentState getCurrentState(String sku) {
        CurrentState currentState = new CurrentState();
        if (snapshots.containsKey(sku)) {
            currentState = snapshots.get(sku).get(snapshots.size() - 1);
        }
        return currentState;
    }

    public void createSnapshot(Product product) {
        if (snapshots.containsKey(product.sku)) {
            snapshots.get(product.sku).add(product.currentState);
        } else {
            snapshots.put(product.sku, new ArrayList<>(List.of(product.currentState)));
        }
    }
}
