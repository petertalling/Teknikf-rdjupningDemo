import java.util.ArrayList;
import java.util.List;

public class ProductInfo {
    private List<EventInterface> eventList = new ArrayList<>();
    private CurrentState snapshot;
    private int firstEventAfterSnapshot;
}
